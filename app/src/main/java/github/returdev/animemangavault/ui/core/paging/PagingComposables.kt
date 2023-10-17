package github.returdev.animemangavault.ui.core.paging

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import github.returdev.animemangavault.R
import github.returdev.animemangavault.core.extensions.toReducedAnimeUi
import github.returdev.animemangavault.core.extensions.toReducedMangaUi
import github.returdev.animemangavault.ui.core.composables.ErrorIcon
import github.returdev.animemangavault.ui.core.composables.ErrorText
import github.returdev.animemangavault.ui.core.composables.RetryButton
import github.returdev.animemangavault.ui.core.composables.items.LongItem
import github.returdev.animemangavault.ui.core.getErrorMessageResource
import github.returdev.animemangavault.ui.model.basic.BasicAnimeUi
import github.returdev.animemangavault.ui.model.basic.BasicMangaUi
import github.returdev.animemangavault.ui.model.reduced.ReducedVisualMediaUi

@Composable
fun <T : Any> PagingRefreshStateLaunchedEffect(
    pagingItemsState : LazyPagingItems<T>,
    changePagingRefreshState : (PagingRefreshState) -> Unit
){

    LaunchedEffect(key1 = pagingItemsState.loadState){

        pagingItemsState.apply {

            if (loadState.mediator?.refresh is LoadState.Loading) {
                changePagingRefreshState(PagingRefreshState.Loading)
            }
            else if (loadState.mediator?.refresh is LoadState.NotLoading &&
                loadState.source.refresh is LoadState.NotLoading
            ) {
                changePagingRefreshState(PagingRefreshState.Loaded(
                    isEmpty = pagingItemsState.itemCount == 0)
                )
            }
            else if (loadState.refresh is LoadState.Error) {
                changePagingRefreshState(
                    PagingRefreshState.Error(
                        (loadState.refresh as LoadState.Error).error.getErrorMessageResource()
                    ) { retry() }
                )
            }

        }

    }

}

@Composable
fun <T:Any> PagingAppendStateLaunchedEffect(
    pagingItemsState : LazyPagingItems<T>,
    changePagingAppendState : (PagingAppendState) -> Unit
) {

    LaunchedEffect(key1 = pagingItemsState.loadState) {

        pagingItemsState.apply {

            if (loadState.append is LoadState.Loading) {

                changePagingAppendState(PagingAppendState.Loading)

            } else if (
                loadState.mediator?.append is LoadState.NotLoading &&
                loadState.source.append is LoadState.NotLoading &&
                loadState.append is LoadState.NotLoading
            ) {
                changePagingAppendState( PagingAppendState.Loaded )
            } else if (loadState.append is LoadState.Error) {
                changePagingAppendState(
                    PagingAppendState.Error(
                        errorRes = (loadState.append as LoadState.Error).error.getErrorMessageResource()
                    ){
                        retry()
                    }
                )
            }

        }

    }
}

@Composable
fun PagingContentLoading(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator()
    }
}

@Composable
fun PagingContentEmpty(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = stringResource(R.string.no_results_found),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
        )
    }
}

@Composable
fun PagingNextPageError(
    @StringRes errorResource : Int,
    retry : () -> Unit
){

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            ErrorIcon()
            ErrorText(
                modifier = Modifier.padding(start = 4.dp),
                errorResource = errorResource,
                textStyle = MaterialTheme.typography.titleMedium
            )

        }

        RetryButton (
            modifier = Modifier.padding(top = 8.dp),
            retry = retry
        )

    }

}

@Composable
fun PagingAnimeContentLoaded(
    animeData : LazyPagingItems<BasicAnimeUi>,
    onClick : (ReducedVisualMediaUi) -> Unit
){

    var appendState by remember {
        mutableStateOf<PagingAppendState>(PagingAppendState.Loaded)
    }

    PagingAppendStateLaunchedEffect(
        pagingItemsState = animeData,
        changePagingAppendState = { newState -> appendState = newState}
    )

    Box(modifier = Modifier.fillMaxSize()){
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(count = animeData.itemCount, key = animeData.itemKey { it.id }) {index ->

                animeData[index]?.let {

                    LongItem(
                        title = it.title,
                        score = it.score,
                        typeRes = it.type.stringResource,
                        imageUrl = it.images[1].url,
                        genres = it.genres,
                        demographicRes = it.demographics.map { d -> d.stringResource }
                    ) {
                        onClick(it.toReducedAnimeUi())
                    }

                }

            }

            if (appendState is PagingAppendState.Error){
                item {
                    PagingNextPageError(
                        errorResource = (appendState as PagingAppendState.Error).errorRes
                    ) {
                        animeData.retry()
                    }
                }
            }else if (appendState is PagingAppendState.Loading){

                item {
                    PagingContentLoading(modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                    )
                }

            }

        }

    }

}

@Composable
fun PagingMangaContentLoaded(
    mangaData : LazyPagingItems<BasicMangaUi>,
    onClick : (ReducedVisualMediaUi) -> Unit
) {

    var appendState by remember {
        mutableStateOf<PagingAppendState>(PagingAppendState.Loaded)
    }

    PagingAppendStateLaunchedEffect(
        pagingItemsState = mangaData,
        changePagingAppendState = { newState -> appendState = newState }
    )

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(count = mangaData.itemCount, key = mangaData.itemKey { it.id }) { index ->

                mangaData[index]?.let {

                    LongItem(
                        title = it.title,
                        score = it.score,
                        typeRes = it.type.stringResource,
                        imageUrl = it.images[1].url,
                        genres = it.genres,
                        demographicRes = it.demographics.map { d -> d.stringResource }
                    ) {
                        onClick(it.toReducedMangaUi())
                    }

                }

            }

            if (appendState is PagingAppendState.Error) {
                item {
                    PagingNextPageError(
                        errorResource = (appendState as PagingAppendState.Error).errorRes
                    ) {
                        mangaData.retry()
                    }
                }
            } else if (appendState is PagingAppendState.Loading) {

                item {
                    PagingContentLoading(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }

            }

        }

    }
}