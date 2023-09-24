package github.returdev.animemangavault.ui.home

import androidx.annotation.StringRes
import github.returdev.animemangavault.domain.model.reduced.ReducedVisualMedia

sealed class HomeUiState{
    object Loading : HomeUiState()
    data class Success(val visualMediaList : List<ReducedVisualMedia>) : HomeUiState()
    data class Error(@StringRes val errorResource : Int, val isRetryAvailable : Boolean) : HomeUiState()
}
