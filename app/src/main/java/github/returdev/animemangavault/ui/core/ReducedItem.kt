package github.returdev.animemangavault.ui.core



import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import github.returdev.animemangavault.R
import github.returdev.animemangavault.domain.model.reduced.ReducedAnime
import github.returdev.animemangavault.domain.model.reduced.ReducedVisualMedia
import github.returdev.animemangavault.ui.theme.AnimeMangaVaultTheme


@Composable
fun <T : ReducedVisualMedia> ReducedItem(
    reducedVisualMedia : T,
    onClick : () -> Unit
){

    var cardModifier = Modifier.clickable { onClick() }
    if (reducedVisualMedia.score >= 9){
        cardModifier = cardModifier.border(width = 2.dp, Color.Yellow)
    }

    Card(modifier = cardModifier) {
        Column(
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .padding(8.dp)
        ) {

            ItemHeader(reducedVisualMedia.score)
            ItemImage(reducedVisualMedia.imageUrl, reducedVisualMedia.defaultTitle)
            ItemFooter(reducedVisualMedia.defaultTitle)

        }

    }

}


@Composable
private fun ItemHeader(score : Float){

    //TODO Review colors when the Theme has been applied.
    val iconColor = if (score >= 9){
        Color.Yellow
    }else if (score >= 7){
        Color.Green
    }else if (score >= 5){
        Color.Magenta
    }else{
        Color.Red
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(R.drawable.ic_score_triple_stars),
            contentDescription = stringResource(R.string.icon_score_description),
            tint = iconColor
        )
        Text(text = score.toString())
    }
}

@Composable
private fun ItemImage(imageUrl : String, title: String){
    AsyncImage(
        model = imageUrl ,
        contentDescription = stringResource(R.string.visual_media_cover_content_description, title),
        modifier = Modifier
            .width(125.dp)
            .height(187.5.dp)
            .padding(vertical = 4.dp)
    )
}

@Composable
private fun ItemFooter(title : String){
    Text(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center,
        text = title,
        overflow = TextOverflow.Ellipsis,
        maxLines = 2,
    )
}


@Preview(
    showBackground = true
)
@Composable
fun PreviewItem(){
    AnimeMangaVaultTheme {
        Column(Modifier.fillMaxSize()) {
            ReducedItem(ReducedAnime(
                1,
                "",
                "Anime Test text of the compose tempt",
                9.5f
            )){}
        }
    }
}