package github.returdev.animemangavault.ui.screen.detailed

import androidx.annotation.StringRes
import github.returdev.animemangavault.ui.model.filters.library.UserLibraryVisualMediaStatesUi

data class DetailedItemLibraryState(
    val state : UserLibraryVisualMediaStatesUi? = null,
    val message : ChangedStateMessage = ChangedStateMessage()
){
    data class ChangedStateMessage(
        val showMessage : Boolean = false,
        @StringRes val messageRes : Int = 0
    )
}
