package github.returdev.animemangavault.ui.core.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import github.returdev.animemangavault.R

@Composable
fun ErrorIcon(
    modifier : Modifier = Modifier
) {

    Box(modifier = modifier.size(24.dp)
        .clip(CircleShape)
        .background(MaterialTheme.colorScheme.errorContainer),
        contentAlignment = Alignment.Center
    )
    {

        Icon(
            modifier= Modifier.fillMaxWidth().aspectRatio(1f),
            painter = painterResource(id = R.drawable.ic_error),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error
        )

    }

}

@Composable
fun RetryButton(
    retry : () -> Unit
){
    Button(
        modifier = Modifier.padding(top = 20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.errorContainer,
            contentColor = MaterialTheme.colorScheme.onErrorContainer
        ),
        onClick = { retry() }
    ) {

        Icon(
            modifier= Modifier.padding(4.dp),
            imageVector = Icons.Default.Refresh,
            contentDescription = null
        )
        Text(text = stringResource(id = R.string.retry))

    }
}

@Preview
@Composable
fun Prevss() {

    Column(Modifier.fillMaxSize()) {

        ErrorIcon()

    }

}