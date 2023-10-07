package github.returdev.animemangavault.ui.core.composables

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun CoverImageView(
    modifier: Modifier = Modifier,
    imageUrl : String
){

    AsyncImage(
        model = imageUrl ,
        contentScale = ContentScale.Crop,
        contentDescription = null,
        modifier = modifier
            .width(125.dp)
            .height(187.5.dp)
            .clip(RoundedCornerShape(8.dp))
    )

}