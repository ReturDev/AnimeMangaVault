package github.returdev.animemangavault.core.exceptions

import androidx.annotation.StringRes
import github.returdev.animemangavault.R

class NetworkException : RuntimeException() {
    @StringRes val stringRes : Int = R.string.network_connection_error
}