package github.returdev.animemangavault.ui.core

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import github.returdev.animemangavault.R


@Composable
fun VisualSearchBar(onClick : () -> Unit){
    //TODO Change the Color.
    Box(
        Modifier
            .clickable{onClick()}
            .padding(20.dp)
            .clip(RoundedCornerShape(50))
            .background(Color.Cyan)
    ) {

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.search_bar_search),
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = stringResource(R.string.search_bar_icon_content_desc)
            )
        }

    }
}
