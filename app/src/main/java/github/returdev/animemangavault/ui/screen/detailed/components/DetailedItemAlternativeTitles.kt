package github.returdev.animemangavault.ui.screen.detailed.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import github.returdev.animemangavault.R
import github.returdev.animemangavault.core.model.components.Title


@Composable
fun DetailedItemAlternativeTitles(titles : List<Title>){

    TitleView<Title.JapaneseTitle>(
        titleTypeRes = R.string.title_language_japanese,
        titles = titles
    )

    TitleView<Title.EnglishTitle>(
        titleTypeRes = R.string.title_language_english,
        titles = titles
    )

    TitleView<Title.SynonymTitle>(
        titleTypeRes = R.string.title_language_synonyms,
        titles = titles
    )
}

@Composable
private inline fun <reified T : Title> TitleView(
    @StringRes titleTypeRes : Int,
    titles : List<Title>
){

    ConstraintLayout(modifier = Modifier.padding(top = 8.dp)) {

        val (titleTypeCardCons, titleCardCons) = createRefs()

        OutlinedCard(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(titleCardCons) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {

            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                titles.filterIsInstance<T>().forEach {
                    Text(
                        text = it.title
                    )
                }
            }

        }

        OutlinedCard(
            modifier = Modifier.constrainAs(titleTypeCardCons) {
                    start.linkTo(titleCardCons.start)
                    top.linkTo(titleCardCons.top)
                    bottom.linkTo(titleCardCons.top)
                },
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                text = stringResource(id = titleTypeRes),
                style = MaterialTheme.typography.titleMedium
            )
        }

    }
}