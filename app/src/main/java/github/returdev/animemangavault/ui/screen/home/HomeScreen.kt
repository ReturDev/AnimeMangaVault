package github.returdev.animemangavault.ui.screen.home

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import github.returdev.animemangavault.R
import github.returdev.animemangavault.ui.core.composables.LoadingReducedItem
import github.returdev.animemangavault.ui.core.composables.ReducedItem
import github.returdev.animemangavault.ui.core.composables.VisualSearchBar
import github.returdev.animemangavault.ui.model.reduced.ReducedVisualMediaUi

@Composable
fun HomeScreen(
    modifier: Modifier,
    navController : NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
){

    val topAnimeState = homeViewModel.topAnimeUiState.collectAsState()
    val topMangaState = homeViewModel.topMangaUiState.collectAsState()
    val currentSeason = homeViewModel.animeCurrentSeason.collectAsState()

    Column(
        modifier.verticalScroll(rememberScrollState())
    ){

        VisualSearchBar {/*TODO Insert click method*/}

        HorizontalListContainer(
            headerText = stringResource(R.string.top_anime),
            homeUiState = topAnimeState.value
        ) {homeViewModel.reloadTopAnimes()}

        Spacer(modifier = Modifier.height(40.dp))

        HorizontalListContainer(
            headerText = stringResource(R.string.top_manga),
            homeUiState = topMangaState.value
        ) {homeViewModel.reloadTopMangas()}
        Spacer(modifier = Modifier.height(40.dp))
        HorizontalListContainer(
            headerText = stringResource(R.string.this_season),
            homeUiState = currentSeason.value
        ) {homeViewModel.reloadCurrentSeason()}

        Spacer(modifier = Modifier.height(16.dp))

    }
}


@Composable
fun HorizontalListContainer(
    headerText : String,
    homeUiState: HomeUiState,
    retry: () -> Unit
    ){
    Column {
        Text(
            text = headerText,
            modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Crossfade(targetState = homeUiState, label = "home_crossfade") {
            when(it){
                HomeUiState.Loading -> LoadingHorizontalList()
                is HomeUiState.Success -> HorizontalList(visualMediaList = it.visualMediaList)
                is HomeUiState.Error.ConnectionError -> ErrorLayout(
                    errorState = it,
                    retry = retry
                )
                is HomeUiState.Error.GenericError -> ErrorLayout(
                    errorState = it,
                    retry = retry
                )
            }
        }
    }

}

@Composable
private fun ErrorLayout(
    errorState : HomeUiState.Error,
    retry : (() -> Unit)
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 187.5.dp)
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Row {

            Icon(
                painter = painterResource(R.drawable.ic_error),
                contentDescription = null
            )
            Text(text = stringResource(errorState.errorResource))

        }
        if (errorState.isRetryAvailable){
            Button(onClick = { retry() }) {
                Text(text = "Retry")
            }
        }

    }
}

@Composable
private fun HorizontalList(visualMediaList: List<ReducedVisualMediaUi>){

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ){
        items(visualMediaList){
            ReducedItem(reducedVisualMedia = it){}
        }
    }
}

@Composable
private fun LoadingHorizontalList(){
    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        LoadingReducedItem()
        LoadingReducedItem()
        LoadingReducedItem()
    }
}

