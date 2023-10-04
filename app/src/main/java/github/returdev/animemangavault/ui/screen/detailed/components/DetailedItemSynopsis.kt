package github.returdev.animemangavault.ui.screen.detailed.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import github.returdev.animemangavault.R
import github.returdev.animemangavault.ui.theme.AnimeMangaVaultTheme

@Composable
fun DetailedItemSynopsis(
    modifier : Modifier,
    synopsis : String
){

    var hasMoreLines by remember { mutableStateOf(false) }
    var textExpanded by remember { mutableStateOf(false)  }

    val maxLines = if (textExpanded) Int.MAX_VALUE else 4

    Text(
        modifier = Modifier.animateContentSize(animationSpec = tween(200)),
        minLines = 4,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        text = synopsis,
        onTextLayout = {
            if (!textExpanded){
                hasMoreLines = it.hasVisualOverflow
            }
        }
    )

    if (hasMoreLines){
        TextButton(
            modifier = modifier,
            onClick = {
                textExpanded = !textExpanded
            }
        ) {
            val textRes = if (textExpanded) R.string.show_less else R.string.show_more
            Text(text = stringResource(id = textRes))
        }
    }

}


@Preview(
    showBackground = true
)
@Composable
fun PreviewDetailedScreen(@PreviewParameter(LoremIpsum::class) text: String){
    AnimeMangaVaultTheme {

        Column(Modifier.fillMaxSize()) {
            DetailedItemSynopsis(modifier = Modifier.align(Alignment.End), synopsis = text)
        }


    }
}