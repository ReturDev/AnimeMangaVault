package github.returdev.animemangavault.ui.model.filters.library

import androidx.annotation.StringRes
import github.returdev.animemangavault.R

/**
 * Enumeration representing the possible states of a visual media item in the library.
 *
 * @param stringResource The string resource ID representing the display name of the state.
 */
enum class UserLibraryVisualMediaStatesUi(@StringRes val stringResource : Int) {
    FOLLOWING(R.string.library_vm_state_following),
    FAVOURITES(R.string.library_vm_state_favourites),
    COMPLETED(R.string.library_vm_state_completed),
    ON_HOLD(R.string.library_vm_state_on_hold),
    DROPPED(R.string.library_vm_state_dropped)
}