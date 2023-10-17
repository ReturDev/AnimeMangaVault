package github.returdev.animemangavault.ui.screen.showmore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import github.returdev.animemangavault.core.extensions.toAnimeFilters
import github.returdev.animemangavault.core.extensions.toBasicAnimeUi
import github.returdev.animemangavault.core.extensions.toBasicMangaUi
import github.returdev.animemangavault.core.extensions.toMangaFilters
import github.returdev.animemangavault.domain.search.SearchAnimeUseCase
import github.returdev.animemangavault.domain.search.SearchMangaUseCase
import github.returdev.animemangavault.domain.showmore.GetAnimeCurrentSeasonPagingUseCase
import github.returdev.animemangavault.ui.model.basic.BasicAnimeUi
import github.returdev.animemangavault.ui.model.basic.BasicMangaUi
import github.returdev.animemangavault.ui.model.filters.core.SortDirectionUi
import github.returdev.animemangavault.ui.model.filters.search.AnimeOrderByUi
import github.returdev.animemangavault.ui.model.filters.search.MangaOrderByUi
import github.returdev.animemangavault.ui.model.filters.search.SearchFiltersUi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class ShowMoreViewModel @Inject constructor(
    private val getAnimeSearch : SearchAnimeUseCase,
    private val getMangaSearch : SearchMangaUseCase,
    private val getCurrentSeason : GetAnimeCurrentSeasonPagingUseCase
) : ViewModel() {

    var animeData : Flow<PagingData<BasicAnimeUi>> = emptyFlow()
    var mangaData : Flow<PagingData<BasicMangaUi>> = emptyFlow()

    val action : (ShowMoreUiAction) -> Unit

    init {

        val actionFlow = MutableSharedFlow<ShowMoreUiAction>()

        animeData = actionFlow.distinctUntilChanged()
            .filter {action ->
                action !is ShowMoreUiAction.GetTopManga
            }
            .flatMapLatest { action ->
            if (action is ShowMoreUiAction.GetTopAnime){
                getAnimeSearch(
                    title = "",
                    filters = SearchFiltersUi.AnimeFiltersUi(
                        orderBy = AnimeOrderByUi.SCORE,
                        sort = SortDirectionUi.DESCENDANT
                    ).toAnimeFilters()
                ).map { pagingData -> pagingData.map { it.toBasicAnimeUi() } }
            }else {
                getCurrentSeason().map { pagingData -> pagingData.map { it.toBasicAnimeUi() } }
            }
        }.cachedIn(viewModelScope)

        mangaData = actionFlow.distinctUntilChanged()
            .filterIsInstance<ShowMoreUiAction.GetTopManga>()
            .flatMapLatest {
                getMangaSearch(
                    title = "",
                    filters = SearchFiltersUi.MangaFiltersUi(
                        orderBy = MangaOrderByUi.SCORE,
                        sort = SortDirectionUi.DESCENDANT
                    ).toMangaFilters()
                ).map { pagingData -> pagingData.map { it.toBasicMangaUi() } }
            }.cachedIn(viewModelScope)

        action = {
            viewModelScope.launch {
                actionFlow.emit(it)
            }
        }

    }



}