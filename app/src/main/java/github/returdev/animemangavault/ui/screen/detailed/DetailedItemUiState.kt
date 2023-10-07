package github.returdev.animemangavault.ui.screen.detailed

import androidx.annotation.StringRes
import github.returdev.animemangavault.R
import github.returdev.animemangavault.ui.model.full.FullVisualMediaUi

sealed class DetailedItemUiState {

    object Loading : DetailedItemUiState()
    data class Success (val vmData : FullVisualMediaUi) : DetailedItemUiState()
    sealed class Error(@StringRes var errorResource : Int, val isRetryAvailable : Boolean) : DetailedItemUiState(){

        object GenericError : Error(R.string.generic_error,true)
        object ConnectionError : Error(R.string.network_connection_error,false)

    }
}