package github.returdev.animemangavault.ui.screen.detailed.components

import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedSuggestionChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import github.returdev.animemangavault.R
import github.returdev.animemangavault.ui.core.composables.CoverImageView
import github.returdev.animemangavault.ui.core.composables.ScoreView
import github.returdev.animemangavault.ui.model.full.FullAnimeUi
import github.returdev.animemangavault.ui.model.full.FullMangaUi
import github.returdev.animemangavault.ui.model.full.FullVisualMediaUi


@Composable
fun DetailedItemInformation(
    visualMediaUi: FullVisualMediaUi
){

    InformationContent(vmInfo = visualMediaUi)

    InformationBottom(vmInfo = visualMediaUi)

}

@Composable
private fun InformationContent(
    vmInfo : FullVisualMediaUi
){

    val typeRes = when(vmInfo){
        is FullAnimeUi -> vmInfo.type.stringResource
        is FullMangaUi -> vmInfo.type.stringResource
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        ConstraintLayout {

            val (scoreCons, imageCons, typeCons) = createRefs()

            createVerticalChain(scoreCons,imageCons, chainStyle = ChainStyle.Packed)

            CoverImageView(
                modifier = Modifier.constrainAs(imageCons) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .border(
                        3.dp,
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.secondaryContainer
                    ),
                imageUrl = vmInfo.images[1].url
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
                onClick = {},
                enabled = false,
                label = {
                    Text(
                        text = stringResource(id = typeRes)
                    )
                }
            )



            ScoreView(
                modifier = Modifier.constrainAs(scoreCons){
                    start.linkTo(imageCons.start)
                },
                score = vmInfo.score
            )

        }


        val informationModifier = Modifier.defaultMinSize(minHeight = 187.5.dp)


        when(vmInfo){
            is FullAnimeUi -> {
                AnimeInformationView(
                    modifier = informationModifier,
                    animeInfo = vmInfo
                )
            }
            is FullMangaUi -> {
                MangaInformationView(
                    modifier = informationModifier,
                    mangaInfo = vmInfo
                )
            }
        }

    }

}

@Composable
private fun AnimeInformationView(
    modifier: Modifier,
    animeInfo : FullAnimeUi
){

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        Row {
            InformationItem(
                modifier = Modifier.weight(1f),
                propertyLabel = R.string.rank,
                value = animeInfo.rank.toString()
            )
            InformationItem(
                modifier = Modifier.weight(1f),
                propertyLabel = R.string.vm_information_status,
                value = stringResource(id = animeInfo.status.stringResource)
            )
        }

        Row {
            InformationItem(
                modifier = Modifier.weight(1f),
                propertyLabel = R.string.vm_information_scorers,
                value = animeInfo.numberOfScorers.toString()
            )
            InformationItem(
                modifier = Modifier.weight(1f),
                propertyLabel = R.string.vm_information_source,
                value = animeInfo.source
            )
        }
        Row {
            InformationItem(
                modifier = Modifier.weight(1f),
                propertyLabel = R.string.vm_information_episodes,
                value = animeInfo.episodes.toString()
            )
            InformationItem(
                modifier = Modifier.weight(1f),
                propertyLabel = R.string.vm_information_season,
                value = animeInfo.season?.let { stringResource(id = it.stringResource) } ?: "-"
            )

        }

        InformationItem(
            modifier = Modifier.fillMaxWidth(),
            propertyLabel = R.string.vm_information_aired,
            value = transformReleasedString(releasedString = animeInfo.aired.toString())
        )


    }

}

@Composable
private fun MangaInformationView(
    modifier: Modifier,
    mangaInfo : FullMangaUi
){

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        Row {
            InformationItem(
                modifier = Modifier.weight(1f),
                propertyLabel = R.string.rank,
                value = mangaInfo.rank.toString()
            )
            InformationItem(
                modifier = Modifier.weight(1f),
                propertyLabel = R.string.vm_information_status,
                value = stringResource(id = mangaInfo.status.stringResource)
            )
        }

        Row {
            InformationItem(
                modifier = Modifier.weight(1f),
                propertyLabel = R.string.vm_information_volumes,
                value = mangaInfo.volumes.toString()
            )
            InformationItem(
                modifier = Modifier.weight(1f),
                propertyLabel = R.string.vm_information_chapters,
                value = mangaInfo.chapters.toString()
            )
        }

        InformationItem(
            modifier = Modifier.fillMaxWidth(),
            propertyLabel = R.string.vm_information_scorers,
            value = mangaInfo.numberOfScorers.toString()
        )

        InformationItem(
            modifier = Modifier.fillMaxWidth(),
            propertyLabel = R.string.vm_information_published,
            value = transformReleasedString(releasedString = mangaInfo.published.toString())
        )

    }

}


@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun InformationBottom(
    vmInfo: FullVisualMediaUi
) {

    val genreTextList = vmInfo.genres.map { stringResource(id = it.stringResource) }
    val demographicTextList = when(vmInfo){
        is FullAnimeUi -> vmInfo.demographics.map { stringResource(id = it.stringResource) }
        is FullMangaUi -> vmInfo.demographics.map { stringResource(id = it.stringResource) }
    }

    val combinedList = genreTextList.plus(demographicTextList).sorted()

    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        combinedList.forEach {
            SuggestionChip(
                enabled = false,
                colors = SuggestionChipDefaults.suggestionChipColors(
                    disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    disabledLabelColor = MaterialTheme.colorScheme.onSecondaryContainer
                ),
                onClick = {},
                label = { Text(text = it)}
            )
        }
    }

}

@Composable
private fun InformationItem(
    modifier: Modifier,
    @StringRes propertyLabel : Int,
    value : String
){

    Column(
        modifier = modifier.padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 4.dp),
            text = stringResource(propertyLabel),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 4.dp),
            textAlign = TextAlign.Center,
            text = value
        )

    }

}

@Composable
private fun transformReleasedString(releasedString : String): String {
    val releasedDateList = releasedString.split("-")
    var transformedString = stringResource(R.string.vm_information_aired_from,releasedDateList[0].trim())
    transformedString = "$transformedString\n${stringResource(R.string.vm_information_aired_to,releasedDateList[1].trim())}"

    return transformedString
}



