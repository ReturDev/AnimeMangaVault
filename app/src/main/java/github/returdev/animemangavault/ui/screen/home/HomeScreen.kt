package github.returdev.animemangavault.ui.screen.home

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import github.returdev.animemangavault.R
import github.returdev.animemangavault.ui.core.composables.ErrorIcon
import github.returdev.animemangavault.ui.core.composables.LoadingReducedItem
import github.returdev.animemangavault.ui.core.composables.ReducedItem
import github.returdev.animemangavault.ui.core.composables.RetryButton
import github.returdev.animemangavault.ui.core.composables.VisualSearchBar
import github.returdev.animemangavault.ui.core.navigation.Destination
import github.returdev.animemangavault.ui.model.filters.core.VisualMediaTypes
import github.returdev.animemangavault.ui.model.reduced.ReducedVisualMediaUi

@Composable
fun HomeScreen(
    modifier: Modifier,
    navController : NavHostController,
    snackBarHostState : SnackbarHostState,
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
            homeUiState = topAnimeState.value,
            retry = {homeViewModel.reloadTopAnimes()},
            onClickElement = {vm -> navigateToDetails(navController,vm) }
        )

        Spacer(modifier = Modifier.height(40.dp))

        HorizontalListContainer(
            headerText = stringResource(R.string.top_manga),
            homeUiState = topMangaState.value,
            retry =  {homeViewModel.reloadTopMangas()},
            onClickElement = {vm -> navigateToDetails(navController,vm) }
        )
        Spacer(modifier = Modifier.height(40.dp))
        HorizontalListContainer(
            headerText = stringResource(R.string.this_season),
            homeUiState = currentSeason.value,
            retry =  {homeViewModel.reloadCurrentSeason()},
            onClickElement = {vm -> navigateToDetails(navController,vm) }
        )

        Spacer(modifier = Modifier.height(16.dp))

    }
}


@Composable
fun HorizontalListContainer(
    headerText : String,
    homeUiState: HomeUiState,
    retry: () -> Unit,
    onClickElement : (ReducedVisualMediaUi) -> Unit
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
                is HomeUiState.Success -> HorizontalList(visualMediaList = it.visualMediaList) { vm ->
                    onClickElement(vm)
                }
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
    retry : () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 187.5.dp)
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            ErrorIcon()
            Text(
                text = stringResource(errorState.errorResource),
                color = MaterialTheme.colorScheme.error
            )

        }
        if (errorState.isRetryAvailable){
            RetryButton { retry() }
        }

    }
}

@Composable
private fun HorizontalList(
    visualMediaList: List<ReducedVisualMediaUi>,
    onClickElement: (ReducedVisualMediaUi) -> Unit
){

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ){
        items(visualMediaList){
            ReducedItem(reducedVisualMedia = it){onClickElement(it)}
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

private fun navigateToDetails(navController: NavHostController, vm : ReducedVisualMediaUi){

    Log.e("DDD", Destination.DetailedItemScreenDestination.fullRoute)
    navController.navigate(route = Destination.DetailedItemScreenDestination(
        vm.id.toString(),
        vm.defaultTitle,
        VisualMediaTypes.getType(vm).name
    ))

}

@Preview
@Composable
fun EE() {
    Column(Modifier.fillMaxSize()) {
        ErrorLayout(errorState = HomeUiState.Error.GenericError) {
            
        }
    }
}