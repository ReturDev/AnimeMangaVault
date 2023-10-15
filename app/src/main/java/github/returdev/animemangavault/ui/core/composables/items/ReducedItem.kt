package github.returdev.animemangavault.ui.core.composables.items



import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.aspectRatio
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
import github.returdev.animemangavault.ui.core.composables.CoverImageView
import github.returdev.animemangavault.ui.core.composables.ScoreView
import github.returdev.animemangavault.ui.core.composables.shimmerBrush
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
        modifier = modifier.width(150.dp).clip(CardDefaults.elevatedShape)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ScoreView(modifier = modifier.fillMaxWidth(),score = reducedVisualMedia.score)
            CoverImageView(modifier = Modifier.fillMaxWidth().aspectRatio(0.67f),imageUrl = reducedVisualMedia.imageUrl)
            Box(modifier = Modifier
                .height(50.dp)
                .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = reducedVisualMedia.defaultTitle,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                )
            }

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