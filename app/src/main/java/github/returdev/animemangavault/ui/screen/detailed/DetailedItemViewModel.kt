package github.returdev.animemangavault.ui.screen.detailed

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import github.returdev.animemangavault.R
import github.returdev.animemangavault.core.annotation.IoDispatcher
import github.returdev.animemangavault.core.exceptions.ApiExceptions
import github.returdev.animemangavault.core.exceptions.NetworkException
import github.returdev.animemangavault.core.extensions.toFullAnimeUi
import github.returdev.animemangavault.core.extensions.toFullMangaUi
import github.returdev.animemangavault.core.extensions.toReducedAnime
import github.returdev.animemangavault.core.extensions.toReducedManga
import github.returdev.animemangavault.core.extensions.toUserLibraryVisualMediaStates
import github.returdev.animemangavault.core.extensions.toUserLibraryVisualMediaStatesUi
import github.returdev.animemangavault.core.network.NetworkConnectivity
import github.returdev.animemangavault.core.network.NetworkState
import github.returdev.animemangavault.domain.detail.GetAnimeByIdUseCase
import github.returdev.animemangavault.domain.detail.GetMangaByIdUseCase
import github.returdev.animemangavault.domain.library.SaveVisualMediaToUserLibraryUseCase
import github.returdev.animemangavault.domain.library.UpdateVisualMediaInUserLibraryUseCase
import github.returdev.animemangavault.domain.library.anime.GetAnimeStateByIdFromUserLibraryUseCase
import github.returdev.animemangavault.domain.library.manga.GetMangaStateByIdFromUserLibraryUseCase
import github.returdev.animemangavault.ui.model.filters.core.VisualMediaTypes
import github.returdev.animemangavault.ui.model.filters.library.UserLibraryVisualMediaStatesUi
import github.returdev.animemangavault.ui.model.full.FullAnimeUi
import github.returdev.animemangavault.ui.model.full.FullMangaUi
import github.returdev.animemangavault.ui.model.full.FullVisualMediaUi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailedItemViewModel @Inject constructor(
    private val getAnimeByIdUseCase: GetAnimeByIdUseCase,
    private val getMangaByIdUseCase: GetMangaByIdUseCase,
    private val networkConnectivity: NetworkConnectivity,
    private val getMangaState : GetMangaStateByIdFromUserLibraryUseCase,
    private val getAnimeState : GetAnimeStateByIdFromUserLibraryUseCase,
    private val saveVisualMediaToLibrary : SaveVisualMediaToUserLibraryUseCase,
    private val updateVisualMediaState : UpdateVisualMediaInUserLibraryUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    companion object{
        private const val ERROR_LOG_TAG = "DetailedItemError"
    }

    private val _uiState = MutableStateFlow<DetailedItemUiState>(DetailedItemUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _inLibraryState = MutableStateFlow(DetailedItemLibraryState())
    val inLibraryState = _inLibraryState.asStateFlow()

    lateinit var id : String
    private lateinit var vmType : VisualMediaTypes

    init {
        viewModelScope.launch {
            _inLibraryState.update {currentState ->
                currentState.copy(
                    state = _uiState.filterIsInstance<DetailedItemUiState.Success>().take(1).map {
                        withContext(dispatcher) {
                            when (vmType) {
                                VisualMediaTypes.ANIME -> getAnimeState(it.vmData.id)
                                VisualMediaTypes.MANGA -> getMangaState(it.vmData.id)
                            }?.toUserLibraryVisualMediaStatesUi()
                        }
                    }.first()
                )
            }
        }
    }

    private val connectionJob : Job = viewModelScope.launch {

        networkConnectivity.networkState.collect{

            if (uiState.value is DetailedItemUiState.Error.ConnectionError){
                reloadDetails()
            }

        }

    }

    fun initData(id: String , vmType : String){
        this.id = id
        this.vmType = VisualMediaTypes.valueOf(vmType)

        load()
    }

    fun reloadDetails() {

        _uiState.value = DetailedItemUiState.Loading
        load()
    }

    private fun load() {
        val action : suspend () -> FullVisualMediaUi = when(vmType){
            VisualMediaTypes.ANIME -> {
                {getAnimeByIdUseCase(id).toFullAnimeUi()}
            }
            VisualMediaTypes.MANGA -> {
                {getMangaByIdUseCase(id).toFullMangaUi()}
            }
        }

        viewModelScope.launch(dispatcher) {

            delay(500)

            _uiState.value = try {

                if (networkConnectivity.networkState.value is NetworkState.Available){

                    connectionJob.cancel()
                    DetailedItemUiState.Success(action())

                }else{

                    DetailedItemUiState.Error.ConnectionError

                }

            }catch (ex : ApiExceptions.ServerInternalException){

                Log.e(ERROR_LOG_TAG, "Server Error")
                val uiState = DetailedItemUiState.Error.GenericError
                uiState.errorResource = ex.stringRes
                uiState

            }catch (ex : ApiExceptions.RateLimitException){

                Log.e(ERROR_LOG_TAG, "Limit reached")
                val uiState = DetailedItemUiState.Error.GenericError
                uiState.errorResource = ex.stringRes
                uiState

            }catch (ex : NetworkException){

                Log.e(ERROR_LOG_TAG, "NetworkException")
                DetailedItemUiState.Error.ConnectionError

            }catch (ex : RuntimeException){

                Log.e(ERROR_LOG_TAG, ex.message.toString())
                DetailedItemUiState.Error.GenericError

            }
        }
    }

    fun saveState(newState: UserLibraryVisualMediaStatesUi?) {

        if (newState == inLibraryState.value.state){
            return
        }

        viewModelScope.launch(dispatcher) {
            val reducedVisualMedia = when(
                val fullVisualMedia: FullVisualMediaUi = (uiState.value as DetailedItemUiState.Success).vmData
            ){
                is FullAnimeUi -> fullVisualMedia.toReducedAnime()
                is FullMangaUi -> fullVisualMedia.toReducedManga()
            }
            if (inLibraryState.value.state == null){
                saveVisualMediaToLibrary(newState!!.toUserLibraryVisualMediaStates(), reducedVisualMedia)
            }else{
                updateVisualMediaState(reducedVisualMedia, newState?.toUserLibraryVisualMediaStates())
            }

            _inLibraryState.update {currentState ->
                currentState.copy(
                    state = newState,
                    message = DetailedItemLibraryState.ChangedStateMessage(
                        showMessage = true,
                        messageRes = if (newState == null){
                            R.string.delete_vm_in_user_library
                        }else{
                            R.string.change_vm_state_in_user_library
                        }
                    )
                )

            }

        }

    }

    fun messageShown() {
        _inLibraryState.update {
            it.copy(
                message = DetailedItemLibraryState.ChangedStateMessage()
            )
        }
    }

}