package github.returdev.animemangavault.ui.core.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import github.returdev.animemangavault.R
import github.returdev.animemangavault.ui.theme.bad_score_star_color
import github.returdev.animemangavault.ui.theme.excellent_score_star_color
import github.returdev.animemangavault.ui.theme.good_score_star_color

@Composable
fun ScoreView(modifier: Modifier = Modifier, score : Float){
    val iconColor = if (score >= 9){
        excellent_score_star_color
    }else if (score >= 5){
        good_score_star_color
    }else{
        bad_score_star_color
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(

            imageVector = Icons.Filled.Star,
            contentDescription = stringResource(R.string.icon_score_description),
            tint = iconColor
        )
        Text(
            text = score.toString(),
            fontWeight = FontWeight.Bold
        )
    }
}