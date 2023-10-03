package github.returdev.animemangavault.ui.screen.home

import androidx.annotation.StringRes
import github.returdev.animemangavault.R
import github.returdev.animemangavault.ui.model.reduced.ReducedVisualMediaUi

sealed class HomeUiState{
    object Loading : HomeUiState()
    data class Success(val visualMediaList : List<ReducedVisualMediaUi>) : HomeUiState()

    sealed class Error(@StringRes var errorResource : Int, val isRetryAvailable : Boolean) : HomeUiState(){

        object GenericError : Error(R.string.generic_error,true)
        object ConnectionError : Error(R.string.network_connection_error,false)

    }

}

