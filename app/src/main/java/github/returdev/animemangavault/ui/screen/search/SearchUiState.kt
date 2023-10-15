package github.returdev.animemangavault.ui.screen.search

import androidx.compose.runtime.Stable
import github.returdev.animemangavault.ui.model.filters.search.SearchFiltersUi
import github.returdev.animemangavault.ui.model.filters.search.SearchFiltersUi.AnimeFiltersUi
import github.returdev.animemangavault.ui.model.filters.search.SearchFiltersUi.MangaFiltersUi

@Stable
sealed class SearchUiState{

    abstract val initializedState : Boolean
    abstract val lastQuerySent : String
    abstract val filtersSelected : SearchFiltersUi
    abstract val numOfFilters : Int
    data class AnimeUiState(
        override val initializedState : Boolean = true,
        override val lastQuerySent : String = "",
        override val filtersSelected : AnimeFiltersUi = AnimeFiltersUi(),
        override val numOfFilters : Int = 0
    ) : SearchUiState()

    data class MangaUiState(
        override val initializedState : Boolean = true,
        override val lastQuerySent : String = "",
        override val filtersSelected : MangaFiltersUi = MangaFiltersUi(),
        override val numOfFilters : Int = 0
    ) : SearchUiState()

}
