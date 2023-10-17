package github.returdev.animemangavault.ui.screen.search

import github.returdev.animemangavault.ui.model.filters.search.SearchFiltersUi

sealed class SearchUiAction{

    sealed class SearchAction : SearchUiAction() {

        abstract val query : String?
        data class SearchAnimeAction(override val query : String?) : SearchAction()
        data class SearchMangaAction(override val query: String?) : SearchAction()

    }

    data class ChangeAnimeFilters(val filters : SearchFiltersUi.AnimeFiltersUi) : SearchUiAction()

    data class ChangeMangaFilters(val filters : SearchFiltersUi.MangaFiltersUi) : SearchUiAction()

    sealed class SnackBarAction : SearchUiAction() {

        object ShowErrorQuerySnackBar : SnackBarAction()

        object ErrorQuerySnackBarShown : SnackBarAction()

    }


}