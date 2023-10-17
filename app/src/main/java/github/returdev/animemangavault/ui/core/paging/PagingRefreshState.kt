package github.returdev.animemangavault.ui.core.paging

import androidx.annotation.StringRes

sealed class PagingRefreshState {

    object Initialized : PagingRefreshState()

    object Loading : PagingRefreshState()

    data class Error(@StringRes val errorRes : Int, val retry: () -> Unit) : PagingRefreshState()

    data class Loaded(val isEmpty : Boolean) : PagingRefreshState()

}