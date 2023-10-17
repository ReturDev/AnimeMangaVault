package github.returdev.animemangavault.ui.screen.showmore

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import github.returdev.animemangavault.R
import github.returdev.animemangavault.ui.core.composables.ErrorLayout
import github.returdev.animemangavault.ui.core.navigation.Destination
import github.returdev.animemangavault.ui.core.navigation.navigateToItemDetails
import github.returdev.animemangavault.ui.core.paging.PagingAnimeContentLoaded
import github.returdev.animemangavault.ui.core.paging.PagingContentEmpty
import github.returdev.animemangavault.ui.core.paging.PagingContentLoading
import github.returdev.animemangavault.ui.core.paging.PagingMangaContentLoaded
import github.returdev.animemangavault.ui.core.paging.PagingRefreshState
import github.returdev.animemangavault.ui.core.paging.PagingRefreshStateLaunchedEffect
import github.returdev.animemangavault.ui.model.basic.BasicAnimeUi
import github.returdev.animemangavault.ui.model.basic.BasicMangaUi
import github.returdev.animemangavault.ui.model.reduced.ReducedVisualMediaUi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

const val SHOW_MORE_TOP_ANIME = "top_anime"
const val SHOW_MORE_TOP_MANGA = "top_manga"
const val SHOW_MORE_THIS_SEASON = "this_season"

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun ShowMoreScreen(
    viewModel: ShowMoreViewModel = hiltViewModel(),
    navController: NavHostController,
    navEntry : NavBackStackEntry
){

    val action by remember {
        mutableStateOf(navEntry.arguments?.getString(Destination.ShowMoreDestination.ACTION))
    }

    Column {
        when(action){
            SHOW_MORE_TOP_ANIME -> {
                viewModel.action(ShowMoreUiAction.GetTopAnime)
                ShowMoreHeader(titleRes = R.string.top_anime ) { navController.popBackStack() }
                ShowMoreAnimeList(
                    animeFlow = viewModel.animeData,
                    onClick = {vm -> navController.navigateToItemDetails(vm) }
                )
            }
            SHOW_MORE_TOP_MANGA -> {
                viewModel.action(ShowMoreUiAction.GetTopManga)
                ShowMoreHeader(titleRes = R.string.top_manga ) { navController.popBackStack() }
                ShowMoreMangaList(
                    mangaFlow = viewModel.mangaData,
                    onClick = {vm -> navController.navigateToItemDetails(vm) }
                )
            }
            SHOW_MORE_THIS_SEASON -> {
                viewModel.action(ShowMoreUiAction.GetThisSeason)
                ShowMoreHeader(titleRes = R.string.this_season ) { navController.popBackStack() }
                ShowMoreAnimeList(
                    animeFlow = viewModel.animeData,
                    onClick = {vm -> navController.navigateToItemDetails(vm) }
                )
            }
        }
    }
    
}

@Composable
private fun ShowMoreHeader(
    @StringRes titleRes : Int,
    navBack : () -> Unit
) {

    Surface(
        color = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp),
        shadowElevation = 8.dp,
        shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
    ){
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
        ) {

            val (backButtonCons, titleCons) = createRefs()

            IconButton(
                modifier = Modifier.constrainAs(backButtonCons){
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
                onClick = navBack
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.navigate_back)
                )
            }

            Text(
                modifier = Modifier.constrainAs(titleCons){
                  start.linkTo(parent.start)
                  top.linkTo(parent.top)
                  end.linkTo(parent.end)
                  bottom.linkTo(parent.bottom)
                }.padding(vertical = 16.dp),
                text = stringResource(id = titleRes),
                style = MaterialTheme.typography.titleLarge
            )


        }
    }
}

@Composable
private fun ShowMoreAnimeList(
    animeFlow : Flow<PagingData<BasicAnimeUi>>,
    onClick : (ReducedVisualMediaUi) -> Unit
){

    val animeData = animeFlow.collectAsLazyPagingItems()
    var pagingState by remember {
        mutableStateOf<PagingRefreshState>(PagingRefreshState.Initialized)
    }

    PagingRefreshStateLaunchedEffect(
        pagingItemsState = animeData,
        changePagingRefreshState = { newState -> pagingState = newState }
    )

    when(pagingState){
        PagingRefreshState.Initialized -> Box(modifier = Modifier.fillMaxSize())
        PagingRefreshState.Loading -> PagingContentLoading()
        is PagingRefreshState.Loaded -> {
            if ((pagingState as PagingRefreshState.Loaded).isEmpty){
                PagingContentEmpty()
            }else {
                PagingAnimeContentLoaded(
                    animeData = animeData,
                    onClick = onClick
                )
            }
        }
        is PagingRefreshState.Error -> {
            val errorState = pagingState as PagingRefreshState.Error
            ErrorLayout(
                modifier = Modifier.fillMaxSize(),
                errorMessageRes = errorState.errorRes,
                showRetryButton = true,
                retry = errorState.retry
            )

        }

    }

}


@Composable
fun ShowMoreMangaList(
    mangaFlow : Flow<PagingData<BasicMangaUi>>,
    onClick: (ReducedVisualMediaUi) -> Unit
) {

    val mangaData = mangaFlow.collectAsLazyPagingItems()
    var pagingState by remember {
        mutableStateOf<PagingRefreshState>(PagingRefreshState.Initialized)
    }

    PagingRefreshStateLaunchedEffect(
        pagingItemsState = mangaData,
        changePagingRefreshState = { newState -> pagingState = newState }
    )

    when(pagingState){
        PagingRefreshState.Initialized -> Box(modifier = Modifier.fillMaxSize())
        PagingRefreshState.Loading -> PagingContentLoading()
        is PagingRefreshState.Loaded -> {
            if ((pagingState as PagingRefreshState.Loaded).isEmpty) {
                PagingContentEmpty()
            } else {
                PagingMangaContentLoaded(
                    mangaData = mangaData,
                    onClick = onClick
                )
            }
        }
        is PagingRefreshState.Error -> {
            val errorState = pagingState as PagingRefreshState.Error
            ErrorLayout(
                modifier = Modifier.fillMaxSize(),
                errorMessageRes = errorState.errorRes,
                showRetryButton = true,
                retry = errorState.retry
            )
        }
    }

}
