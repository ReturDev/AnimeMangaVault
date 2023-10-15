package github.returdev.animemangavault.ui.core.composables.items

import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ElevatedSuggestionChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import github.returdev.animemangavault.R
import github.returdev.animemangavault.ui.core.composables.CoverImageView
import github.returdev.animemangavault.ui.core.composables.ScoreView
import github.returdev.animemangavault.ui.model.components.anime.AnimeDemographics
import github.returdev.animemangavault.ui.model.components.common.Genres
import github.returdev.animemangavault.ui.theme.AnimeMangaVaultTheme

@Composable
fun LongItem(
    title : String,
    score : Float,
    @StringRes typeRes: Int,
    imageUrl: String,
    genres : List<Genres>,
    @StringRes demographicRes: List<Int>,
    onClick : () -> Unit
){

    ElevatedCard(
        modifier = Modifier.fillMaxWidth().clip(CardDefaults.elevatedShape)
            .clickable { onClick() },
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {

            val (scoreCons,titleCons,genresCons,coverCons,demographicCons) = createRefs()

            ScoreView(
                modifier = Modifier.constrainAs(scoreCons){
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                },
                score = score
            )

            LongItemCover(
                modifier = Modifier.constrainAs(coverCons){
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                },
                imageUrl = imageUrl ,
                typeRes = typeRes
            )

            Text(
                modifier = Modifier.padding(4.dp)
                    .constrainAs(titleCons) {
                        top.linkTo(scoreCons.bottom)
                        start.linkTo(coverCons.end)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    },
                text = title,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                maxLines = 2,
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .constrainAs(genresCons) {
                        start.linkTo(coverCons.end)
                        end.linkTo(parent.end)
                        top.linkTo(titleCons.bottom)
                        bottom.linkTo(demographicCons.top)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints

                    },
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                text = genres.map {
                    stringResource(id = it.stringResource)
                }.joinToString(", ")
            )

            DemographicChips(
                modifier = Modifier.constrainAs(demographicCons){
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
                demographicsRes = demographicRes
            )

        }

    }

}


@Composable
fun LongItemCover(
    modifier: Modifier = Modifier,
    imageUrl : String,
    @StringRes typeRes : Int
) {

    ConstraintLayout(modifier) {

        val (imageCons, typeCons) = createRefs()

        CoverImageView(
            modifier = Modifier
                .width(100.dp)
                .aspectRatio(0.67f)
                .constrainAs(imageCons) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .border(
                    3.dp,
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.secondaryContainer
                ),
            imageUrl = imageUrl
        )

        ElevatedSuggestionChip(
            modifier = Modifier.constrainAs(typeCons) {
                start.linkTo(imageCons.start)
                end.linkTo(imageCons.end)
                top.linkTo(imageCons.bottom)
                bottom.linkTo(imageCons.bottom)
            },
            colors = SuggestionChipDefaults.suggestionChipColors(
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledLabelColor = MaterialTheme.colorScheme.onSecondaryContainer
            ),
            onClick = { },
            enabled = false,
            label = {
                Text(text = stringResource(id = typeRes))
            }
        )
    }
}


@Composable
fun DemographicChips(
    modifier: Modifier = Modifier,
    @StringRes demographicsRes : List<Int>
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        demographicsRes.forEach { d ->
            SuggestionChip(
                onClick = { }, label = {
                    Text(text = stringResource(id = d))
                }
            )
        }

    }

}

@Preview
@Composable
fun PrevLong() {

    AnimeMangaVaultTheme {

        Column(Modifier.fillMaxSize()) {

            LongItem(
                title = "Title",
                score = 5.5f,
                typeRes = R.string.anime_type_movie,
                imageUrl = "",
                genres = listOf(
                    Genres.ACTION,
                    Genres.ADVENTURE,
                    Genres.MYSTERY,
                    Genres.COMEDY
                ),
                demographicRes = listOf(
                    AnimeDemographics.JOSEI.stringResource,
                    AnimeDemographics.SHOUJO.stringResource
                )
            ){}

        }

    }

}