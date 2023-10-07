package github.returdev.animemangavault.ui.core.composables



import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import github.returdev.animemangavault.ui.model.reduced.ReducedAnimeUi
import github.returdev.animemangavault.ui.model.reduced.ReducedVisualMediaUi
import github.returdev.animemangavault.ui.theme.AnimeMangaVaultTheme


@Composable
fun ReducedItem(
    modifier: Modifier = Modifier,
    reducedVisualMedia: ReducedVisualMediaUi,
    onClick: () -> Unit
){

    ElevatedCard(
        modifier = modifier
            .clip(CardDefaults.elevatedShape)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Column(
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .padding(8.dp)
        ) {

            ScoreView(score = reducedVisualMedia.score)
            CoverImageView(imageUrl = reducedVisualMedia.imageUrl)
            ItemFooter(reducedVisualMedia.defaultTitle)

        }

    }

}

@Composable
fun LoadingReducedItem(){

    val shimmerColors = listOf(
        MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.7f),
        MaterialTheme.colorScheme.secondaryContainer,
        MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.7f)
    )
    Column(
        modifier = Modifier.width(IntrinsicSize.Min)) {
        Box(modifier = Modifier
            .width(60.dp)
            .height(20.dp)
            .shimmerBrush(shimmerColors)
        )
        Box(modifier = Modifier
            .width(125.dp)
            .height(187.5.dp)
            .padding(vertical = 8.dp)
            .shimmerBrush(shimmerColors)
        )
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .shimmerBrush(shimmerColors)
        )
    }
}

@Composable
private fun ItemFooter(title : String){

    Box(modifier = Modifier
        .height(50.dp)
        .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ){
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = title,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
        )
    }

}


@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showSystemUi = true
)
@Composable
fun PreviewItem(){
    AnimeMangaVaultTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            ReducedItem(reducedVisualMedia = ReducedAnimeUi(
                5, "", "SDDF", 4.4f
            )) {

            }
        }
    }
}