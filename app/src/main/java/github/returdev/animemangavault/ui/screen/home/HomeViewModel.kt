package github.returdev.animemangavault.ui.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import github.returdev.animemangavault.core.annotation.IoDispatcher
import github.returdev.animemangavault.core.exceptions.ApiExceptions
import github.returdev.animemangavault.core.exceptions.NetworkException
import github.returdev.animemangavault.core.extensions.toReducedAnimeUi
import github.returdev.animemangavault.core.extensions.toReducedMangaUi
import github.returdev.animemangavault.core.network.NetworkConnectivity
import github.returdev.animemangavault.core.network.NetworkState
import github.returdev.animemangavault.domain.home.GetAnimeCurrentSeasonUseCase
import github.returdev.animemangavault.domain.home.GetTopAnimesUseCase
import github.returdev.animemangavault.domain.home.GetTopMangasUseCase
import github.returdev.animemangavault.ui.model.reduced.ReducedVisualMediaUi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAnimeCurrentSeason : GetAnimeCurrentSeasonUseCase,
    private val getTopAnimes : GetTopAnimesUseCase,
    private val getTopMangas : GetTopMangasUseCase,
    private val networkConnectivity: NetworkConnectivity,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private companion object{
        const val ERROR_LOG_TAG = "HomeViewModelError"
        const val REQUESTS_LIMIT = 15
        const val PAGE = 1
    }

    private val _topAnimesUiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val topAnimeUiState = _topAnimesUiState.asStateFlow()

    private val _topMangasUiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val topMangaUiState = _topMangasUiState.asStateFlow()

    private val _animeCurrentSeason = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val animeCurrentSeason = _animeCurrentSeason.asStateFlow()

    init{

        setNetworkCollector()

        loadTopAnimes()
        loadTopMangas()
        loadThisSeason()


    }

    private fun setNetworkCollector(){

        viewModelScope.launch(dispatcher) {

            networkConnectivity.networkState.collect{ networkState ->

                if (networkState == NetworkState.Available){

                    if (_topAnimesUiState.value == HomeUiState.Error.ConnectionError){
                        reloadTopAnimes()
                    }

                    if (_topMangasUiState.value == HomeUiState.Error.ConnectionError){
                        reloadTopMangas()
                    }

                    if (_animeCurrentSeason.value == HomeUiState.Error.ConnectionError){
                        reloadCurrentSeason()
                    }

                }

            }

        }

    }

    private fun loadTopAnimes(){

        viewModelScope.launch(dispatcher) {
            _topAnimesUiState.value = loadData {
                getTopAnimes(PAGE, REQUESTS_LIMIT).map { it.toReducedAnimeUi()}
            }
        }

    }

    private fun loadTopMangas(){
        viewModelScope.launch(dispatcher) {
            _topMangasUiState.value = loadData {
                getTopMangas(PAGE, REQUESTS_LIMIT).map { it.toReducedMangaUi() }
            }
        }
    }

    private fun loadThisSeason(){
        viewModelScope.launch(dispatcher) {
            _animeCurrentSeason.value = loadData {
                getAnimeCurrentSeason(PAGE, REQUESTS_LIMIT).map { it.toReducedAnimeUi() }
            }
        }
    }

    private suspend fun loadData(getData : suspend () -> List<ReducedVisualMediaUi>) : HomeUiState {
        return try {

            if (networkConnectivity.networkState.value is NetworkState.Available){
                HomeUiState.Success(getData())
            }else{
                HomeUiState.Error.ConnectionError
            }

        }catch (ex : ApiExceptions.ServerInternalException){
            Log.e(ERROR_LOG_TAG, "Server Error")
            val uiState = HomeUiState.Error.GenericError
            uiState.errorResource = ex.stringRes
            uiState
        }catch (ex : ApiExceptions.RateLimitException){
            Log.e(ERROR_LOG_TAG, "Limit reached")
            val uiState = HomeUiState.Error.GenericError
            uiState.errorResource = ex.stringRes
            uiState
        }catch (ex : NetworkException){
            Log.e(ERROR_LOG_TAG, "NetworkException")
            HomeUiState.Error.ConnectionError
        }catch (ex : RuntimeException){
            Log.e(ERROR_LOG_TAG, ex.message.toString())
            HomeUiState.Error.GenericError
        }
    }

    fun reloadTopAnimes(){
        _topAnimesUiState.value = HomeUiState.Loading
        loadTopAnimes()
    }

    fun reloadTopMangas(){
        _topMangasUiState.value = HomeUiState.Loading
        loadTopMangas()
    }

    fun reloadCurrentSeason(){
        _animeCurrentSeason.value = HomeUiState.Loading
        loadThisSeason()
    }




}