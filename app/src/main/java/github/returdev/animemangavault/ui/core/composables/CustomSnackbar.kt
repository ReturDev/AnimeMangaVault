package github.returdev.animemangavault.ui.core.composables

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import github.returdev.animemangavault.ui.theme.AnimeMangaVaultTheme

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
            if (snackbarData.visuals.actionLabel != null){
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
    ){

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = snackbarData.visuals.message,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium
        )



    }

}