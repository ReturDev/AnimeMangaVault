package github.returdev.animemangavault.ui.core.snackbar

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun rememberSnackBarController(snackbarHostState: SnackbarHostState) : SnackBarController {
    return remember{ SnackBarController(snackbarHostState) }
}

class SnackBarController (
    val snackbarHostState: SnackbarHostState
) {

    private var snackbarType : SnackbarType = SnackbarType.Default

    val Snackbar : @Composable (SnackbarData) -> Unit
        get() = snackbarType.snackbar

    fun showSnackbar(
        coroutineScope: CoroutineScope,
        snackbarType: SnackbarType = SnackbarType.Default,
        message: String,
        actionLabel: String? = null,
        withDismissAction: Boolean = false,
        duration: SnackbarDuration = SnackbarDuration.Short,
        executeBefore : (() -> Unit)? = null,
        executeAfter : (() -> Unit)? = null,
    ){

        coroutineScope.launch {

            executeBefore?.invoke()

            showSnackbar(
                snackbarType,
                message,
                actionLabel,
                withDismissAction,
                duration
            )

            executeAfter?.invoke()
        }
    }

    suspend fun showSnackbar(
        snackbarType: SnackbarType = SnackbarType.Default,
        message: String,
        actionLabel: String? = null,
        withDismissAction: Boolean = false,
        duration: SnackbarDuration = SnackbarDuration.Short
    ){

        this.snackbarType = snackbarType
        snackbarHostState.showSnackbar(message, actionLabel, withDismissAction, duration)

    }

}

sealed class SnackbarType(val snackbar : @Composable (SnackbarData) -> Unit){

    object Default : SnackbarType(
        {
            CustomSnackbar(
                snackbarData = it,
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                actionColor = MaterialTheme.colorScheme.tertiary
            )
        }
    )
    object Error : SnackbarType(
        {
            CustomSnackbar(
                snackbarData = it,
                containerColor = MaterialTheme.colorScheme.errorContainer ,
                contentColor = MaterialTheme.colorScheme.onErrorContainer,
                actionColor = MaterialTheme.colorScheme.error
            )
        }
    )

}