package github.returdev.animemangavault.core.exceptions

import androidx.annotation.StringRes
import github.returdev.animemangavault.R

sealed class ApiExceptions(@StringRes stringRes : Int) : RuntimeException(){
    object ServerInternalException : ApiExceptions(R.string.api_server_internal_error)
    object RateLimitException : ApiExceptions(R.string.generic_error)
}
