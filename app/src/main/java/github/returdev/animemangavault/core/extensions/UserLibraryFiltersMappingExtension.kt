package github.returdev.animemangavault.core.extensions

import github.returdev.animemangavault.core.model.library.UserLibraryOrderBy
import github.returdev.animemangavault.core.model.library.UserLibrarySortDirection
import github.returdev.animemangavault.core.model.library.UserLibraryVisualMediaStates
import github.returdev.animemangavault.ui.model.filters.core.SortDirection
import github.returdev.animemangavault.ui.model.filters.library.UserLibraryOrderByUi
import github.returdev.animemangavault.ui.model.filters.library.UserLibraryVisualMediaStatesUi


fun UserLibraryOrderByUi.toUserLibraryOrderBy() : UserLibraryOrderBy {

    return when(this){
        UserLibraryOrderByUi.ADDED_DATE -> UserLibraryOrderBy.ADDED_DATE
        UserLibraryOrderByUi.DEFAULT_TITLE -> UserLibraryOrderBy.DEFAULT_TITLE
        UserLibraryOrderByUi.SCORE -> UserLibraryOrderBy.SCORE
    }

}

fun SortDirection.toUserLibrarySortDirection() : UserLibrarySortDirection {

    return when(this){
        SortDirection.ASCENDANT -> UserLibrarySortDirection.ASCENDANT
        SortDirection.DESCENDANT -> UserLibrarySortDirection.DESCENDANT
    }

}

fun UserLibraryVisualMediaStatesUi.toUserLibraryVisualMediaStates() : UserLibraryVisualMediaStates {

    return when(this){
        UserLibraryVisualMediaStatesUi.FOLLOWING -> UserLibraryVisualMediaStates.FOLLOWING
        UserLibraryVisualMediaStatesUi.FAVOURITES -> UserLibraryVisualMediaStates.FAVOURITES
        UserLibraryVisualMediaStatesUi.COMPLETED -> UserLibraryVisualMediaStates.COMPLETED
        UserLibraryVisualMediaStatesUi.ON_HOLD -> UserLibraryVisualMediaStates.ON_HOLD
        UserLibraryVisualMediaStatesUi.DROPPED -> UserLibraryVisualMediaStates.DROPPED
    }

}

fun UserLibraryVisualMediaStates.toUserLibraryVisualMediaStatesUi() : UserLibraryVisualMediaStatesUi {

    return when(this){
        UserLibraryVisualMediaStates.FOLLOWING -> UserLibraryVisualMediaStatesUi.FOLLOWING
        UserLibraryVisualMediaStates.FAVOURITES -> UserLibraryVisualMediaStatesUi.FAVOURITES
        UserLibraryVisualMediaStates.COMPLETED -> UserLibraryVisualMediaStatesUi.COMPLETED
        UserLibraryVisualMediaStates.ON_HOLD -> UserLibraryVisualMediaStatesUi.ON_HOLD
        UserLibraryVisualMediaStates.DROPPED -> UserLibraryVisualMediaStatesUi.DROPPED
    }

}