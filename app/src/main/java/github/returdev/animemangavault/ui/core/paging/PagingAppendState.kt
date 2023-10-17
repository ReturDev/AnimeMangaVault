package github.returdev.animemangavault.ui.core.paging

import androidx.annotation.StringRes

sealed class PagingAppendState{

    object Loading : PagingAppendState()

    data class Error(@StringRes val errorRes : Int, val retry: () -> Unit) : PagingAppendState()

    object Loaded : PagingAppendState()

}
