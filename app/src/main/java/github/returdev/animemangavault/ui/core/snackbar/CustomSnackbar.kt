package github.returdev.animemangavault.ui.core.snackbar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CustomSnackbar(
    modifier : Modifier = Modifier,
    snackbarData: SnackbarData,
    containerColor : Color = SnackbarDefaults.color,
    contentColor: Color = SnackbarDefaults.contentColor,
    actionColor : Color = SnackbarDefaults.actionColor
) {

    Snackbar(
        modifier = modifier.padding(16.dp),
        action = {
            if (snackbarData.visuals.actionLabel != null) {
                TextButton(onClick = {}) {
                    Text(
                        text = snackbarData.visuals.actionLabel!!,
                        color = actionColor,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            }
        },
        containerColor = containerColor,
        contentColor = contentColor
    ) {

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = snackbarData.visuals.message,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium
        )


    }

}