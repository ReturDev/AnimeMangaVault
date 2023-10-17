package github.returdev.animemangavault.ui.core

import androidx.annotation.StringRes
import github.returdev.animemangavault.R
import github.returdev.animemangavault.core.exceptions.ApiExceptions
import github.returdev.animemangavault.core.exceptions.NetworkException

@StringRes
fun Throwable.getErrorMessageResource() : Int {

    return  when(this){
        ApiExceptions.ServerInternalException -> {
            ApiExceptions.ServerInternalException.stringRes
        }
        ApiExceptions.RateLimitException -> {
            ApiExceptions.RateLimitException.stringRes
        }
        is NetworkException -> {
            this.stringRes
        }
        else -> {
            R.string.generic_error
        }
    }

}