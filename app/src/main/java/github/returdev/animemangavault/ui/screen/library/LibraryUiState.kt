package github.returdev.animemangavault.ui.screen.library

import github.returdev.animemangavault.ui.model.filters.core.SortDirectionUi
import github.returdev.animemangavault.ui.model.filters.core.VisualMediaTypes
import github.returdev.animemangavault.ui.model.filters.library.UserLibraryOrderByUi
import github.returdev.animemangavault.ui.model.filters.library.UserLibraryVisualMediaStatesUi


data class LibraryUiState(
    val vmTypeSelected : VisualMediaTypes = VisualMediaTypes.ANIME,
    val currentOrderBy : UserLibraryOrderByUi = UserLibraryOrderByUi.ADDED_DATE,
    val currentSortDirection : SortDirectionUi = SortDirectionUi.ASCENDANT,
    val animeStateSelected : UserLibraryVisualMediaStatesUi = UserLibraryVisualMediaStatesUi.FOLLOWING,
    val mangaStateSelected : UserLibraryVisualMediaStatesUi = UserLibraryVisualMediaStatesUi.FOLLOWING,
)