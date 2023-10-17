package github.returdev.animemangavault.ui.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import github.returdev.animemangavault.core.annotation.IoDispatcher
import github.returdev.animemangavault.core.extensions.toAnimeFilters
import github.returdev.animemangavault.core.extensions.toBasicAnimeUi
import github.returdev.animemangavault.core.extensions.toBasicMangaUi
import github.returdev.animemangavault.core.extensions.toMangaFilters
import github.returdev.animemangavault.domain.search.ResetCacheUseCase
import github.returdev.animemangavault.domain.search.SearchAnimeUseCase
import github.returdev.animemangavault.domain.search.SearchMangaUseCase
import github.returdev.animemangavault.ui.model.basic.BasicAnimeUi
import github.returdev.animemangavault.ui.model.basic.BasicMangaUi
import github.returdev.animemangavault.ui.model.filters.core.VisualMediaTypes
import github.returdev.animemangavault.ui.model.filters.search.SearchFiltersUi
import github.returdev.animemangavault.ui.screen.search.SearchUiAction.*
import github.returdev.animemangavault.ui.screen.search.SearchUiAction.SearchAction.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchAnime : SearchAnimeUseCase,
    private val searchManga : SearchMangaUseCase,
    private val clearCache : ResetCacheUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    val animeUiState : StateFlow<SearchUiState.AnimeUiState>
    val basicAnimes : Flow<PagingData<BasicAnimeUi>>

    val mangaUiState : StateFlow<SearchUiState.MangaUiState>
    val basicMangas : Flow<PagingData<BasicMangaUi>>

    val showSnackbarState : StateFlow<Boolean>
    val snackbarShown : () -> Unit

    val search : (String, VisualMediaTypes) -> Unit
    val saveNewFilters : (SearchFiltersUi, VisualMediaTypes) -> Unit


    init {

        viewModelScope.launch {
            withContext(dispatcher){clearCache()}
        }

        val actionSharedFlow = MutableSharedFlow<SearchUiAction>()

        showSnackbarState = initShowSnackbarFlow( actionSharedFlow )

        val animeSearchFlow = actionSharedFlow.filterIsInstance<SearchAnimeAction>()
            .filter { it.query!!.length >= 3 }
            .shareIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                replay = 1
            )
            .onStart { emit(SearchAnimeAction(null)) }


        val animeFilterFlow = actionSharedFlow.filterIsInstance<ChangeAnimeFilters>()
            .distinctUntilChanged()
            .shareIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                replay = 1
            )
            .onStart { emit(ChangeAnimeFilters(SearchFiltersUi.AnimeFiltersUi()))}

        animeUiState = initAnimeUiState(
            animeSearchFlow,
            animeFilterFlow
        )

        basicAnimes = initBasicAnimesFlow(animeSearchFlow)


        val mangaSearchFlow = actionSharedFlow.filterIsInstance<SearchMangaAction>()
            .distinctUntilChanged()
            .shareIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                replay = 1
            )
            .onStart { emit(SearchMangaAction(null)) }

        val mangaFilterFlow = actionSharedFlow.filterIsInstance<ChangeMangaFilters>()
            .shareIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                replay = 1
            )
            .onStart { emit(ChangeMangaFilters(SearchFiltersUi.MangaFiltersUi()))}



        mangaUiState = initMangaUiState(
            mangaSearchFlow,
            mangaFilterFlow
        )

        basicMangas = initBasicMangasFlow( mangaSearchFlow )

        snackbarShown = {
            viewModelScope.launch {
                actionSharedFlow.emit(SnackBarAction.ErrorQuerySnackBarShown)
            }
        }

        search = { query, type ->

            viewModelScope.launch {

                when(type){
                    VisualMediaTypes.ANIME -> actionSharedFlow.emit(SearchAnimeAction(query.trim()))
                    VisualMediaTypes.MANGA -> actionSharedFlow.emit(SearchMangaAction(query.trim()))
                }

            }

        }

        saveNewFilters = { filters, type ->

            viewModelScope.launch {

                when(type){
                    VisualMediaTypes.ANIME -> actionSharedFlow.emit(ChangeAnimeFilters(filters as SearchFiltersUi.AnimeFiltersUi))
                    VisualMediaTypes.MANGA -> actionSharedFlow.emit(ChangeMangaFilters(filters as SearchFiltersUi.MangaFiltersUi))
                }

            }

        }

    }

    private fun initAnimeUiState(
        animeSearchFlow: Flow<SearchAnimeAction>,
        animeChangeFilters: Flow<ChangeAnimeFilters>
    ): StateFlow<SearchUiState.AnimeUiState> {

        val flowsCombine = combine(
            animeSearchFlow.distinctUntilChanged(),
            animeChangeFilters,
            ::Pair
        )

        return flowsCombine.map { (search, newFilter) ->
                SearchUiState.AnimeUiState(
                    lastQuerySent = search.query ?: "",
                    filtersSelected = newFilter.filters,
                    numOfFilters = calculateNumOfFilters(newFilter.filters)
                )
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = SearchUiState.AnimeUiState()
            )

    }

    private fun initBasicAnimesFlow(
        animeSearchFlow: Flow<SearchAnimeAction>,
    ): Flow<PagingData<BasicAnimeUi>> {
        return animeSearchFlow.distinctUntilChanged()
            .filter { it.query != null }
            .flatMapLatest { action ->
                searchAnime(action.query!!, animeUiState.value.filtersSelected.toAnimeFilters())
                    .map { pagingData -> pagingData.map { it.toBasicAnimeUi() } }

            }.cachedIn(viewModelScope)
    }

    private fun initMangaUiState(
        mangaSearchFlow: Flow<SearchMangaAction>,
        mangaChangeFilters: Flow<ChangeMangaFilters>,
    ): StateFlow<SearchUiState.MangaUiState> {

        val flowCombine = combine(
            mangaSearchFlow.distinctUntilChanged(),
            mangaChangeFilters,
            ::Pair
        )

        return flowCombine.map { (search, newFilter) ->
                SearchUiState.MangaUiState(
                    lastQuerySent = search.query ?: "",
                    filtersSelected = newFilter.filters,
                    numOfFilters = calculateNumOfFilters(newFilter.filters),
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = SearchUiState.MangaUiState()
            )

    }

    private fun initBasicMangasFlow(
        mangaSearchFlow: Flow<SearchMangaAction>,
    ): Flow<PagingData<BasicMangaUi>> {

        return mangaSearchFlow.distinctUntilChanged()
            .filter { it.query != null && it.query.length >= 3 }
            .flatMapLatest { action ->
                searchManga(action.query!!, mangaUiState.value.filtersSelected.toMangaFilters())
                    .map { pagingData -> pagingData.map { it.toBasicMangaUi() } }
            }.cachedIn(viewModelScope)

    }

    private fun initShowSnackbarFlow(actionSharedFlow: MutableSharedFlow<SearchUiAction>): StateFlow<Boolean> {
        return actionSharedFlow.transform {
                if (it is SearchAction && (it.query?.length ?: 0) < 3 ){
                    emit(SnackBarAction.ShowErrorQuerySnackBar)
                }else if (it is SnackBarAction){
                    emit(SnackBarAction.ErrorQuerySnackBarShown)
                }
            }
            .map {
                when(it){
                    SnackBarAction.ErrorQuerySnackBarShown -> false
                    SnackBarAction.ShowErrorQuerySnackBar -> true
                }
            }
            .distinctUntilChanged()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = false
            )
    }

    private fun calculateNumOfFilters(filtersUi: SearchFiltersUi): Int {

        var count = 0

        filtersUi.orderBy?.let { count++ }
        filtersUi.status?.let { count++ }
        filtersUi.type?.let { count++ }
        if (filtersUi.genres.isNotEmpty()) count++

        return count

    }

}