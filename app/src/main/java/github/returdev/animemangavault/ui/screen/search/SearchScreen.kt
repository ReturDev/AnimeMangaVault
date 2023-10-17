package github.returdev.animemangavault.ui.screen.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import github.returdev.animemangavault.R
import github.returdev.animemangavault.ui.core.composables.ErrorLayout
import github.returdev.animemangavault.ui.core.navigation.navigateToItemDetails
import github.returdev.animemangavault.ui.core.paging.*
import github.returdev.animemangavault.ui.core.snackbar.SnackBarController
import github.returdev.animemangavault.ui.core.snackbar.SnackbarType
import github.returdev.animemangavault.ui.model.basic.BasicAnimeUi
import github.returdev.animemangavault.ui.model.basic.BasicMangaUi
import github.returdev.animemangavault.ui.model.filters.core.VisualMediaTypes
import github.returdev.animemangavault.ui.model.reduced.ReducedVisualMediaUi
import github.returdev.animemangavault.ui.screen.search.filters.SearchFiltersDialog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    navController : NavHostController,
    snackBarController: SnackBarController
) {

    val animeState by viewModel.animeUiState.collectAsState()
    val mangaState by viewModel.mangaUiState.collectAsState()


    val pagerState = rememberPagerState()
    var currentTypeSelected by remember { mutableStateOf(VisualMediaTypes.values()[pagerState.currentPage]) }

    val animeQueryHistory = remember { mutableListOf<String>() }
    val mangaQueryHistory = remember { mutableListOf<String>() }

    var currentQuery by remember { mutableStateOf("") }

    var showFilterDialog by remember { mutableStateOf(false) }

    ShowSnackbarLaunchedEffect(
        snackBarState = viewModel.showSnackbarState.collectAsState().value,
        snackBarController = snackBarController,
        snackbarShown = viewModel.snackbarShown
    )


    LaunchedEffect(key1 = pagerState){
        snapshotFlow { pagerState.currentPage }.distinctUntilChanged().collect { page ->
            currentTypeSelected = VisualMediaTypes.values()[page]
            currentQuery = if (page == 0)
                animeState.lastQuerySent
            else
                mangaState.lastQuerySent

        }
    }

    AddQueryToAnimeHistoryLaunchedEffect(
        animeQuery = animeState.lastQuerySent,
        addToHistory = { addQueryToHistorySearch(it, animeQueryHistory) }
    )

    AddQueryToMangaHistoryLaunchedEffect(
        mangaQuery = mangaState.lastQuerySent,
        addToHistory = { addQueryToHistorySearch(it, mangaQueryHistory) }
    )

    val currentType = VisualMediaTypes.values()[pagerState.settledPage]

    if (showFilterDialog){

        SearchFiltersDialog(
            onCloseDialog = { showFilterDialog = false },
            currentFilters = when(currentType){
                VisualMediaTypes.ANIME -> animeState.filtersSelected
                VisualMediaTypes.MANGA -> mangaState.filtersSelected
            },
            saveFilters = { filters -> viewModel.saveNewFilters(filters,currentType) }
        )

    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AMVSearchBar(
            currentQuery = { currentQuery } ,
            queryHistory = when(currentType){
                VisualMediaTypes.ANIME -> animeQueryHistory
                VisualMediaTypes.MANGA -> mangaQueryHistory
            },
            currentState = when(currentType){
                VisualMediaTypes.ANIME -> animeState
                VisualMediaTypes.MANGA -> mangaState
            },
            popBackStack = { navController.popBackStack() },
            changeFilters = { showFilterDialog = true  },
            onValueChange = { currentQuery = it} ,
            onSearch = { viewModel.search(it, currentType) }
        )

        SearchTabLayout(
            pagerState = pagerState
        ) { page ->

            if (page == 0){

                SearchAnimeContentList(
                    basicAnimes = viewModel.basicAnimes
                ){ vm -> navController.navigateToItemDetails(vm) }

            }else{

                SearchMangaContentList(
                    basicMangas = viewModel.basicMangas,
                ){ vm -> navController.navigateToItemDetails(vm) }

            }

        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AMVSearchBar(
    modifier : Modifier = Modifier,
    queryHistory : List<String>,
    currentQuery : () -> String,
    currentState : SearchUiState,
    popBackStack : () -> Unit,
    changeFilters : () -> Unit,
    onValueChange : (String) -> Unit,
    onSearch : (String) -> Unit
) {

    var searchBarActive by remember { mutableStateOf(false) }

    SearchBar(
        modifier = modifier.padding(vertical = 16.dp),
        query = currentQuery(),
        onQueryChange = {onValueChange(it)},
        onSearch = {
            onSearch(it)
            searchBarActive = false
        },
        active = searchBarActive,
        onActiveChange = { searchBarActive = it },
        placeholder = {
            Text(text = stringResource(id = R.string.search_bar_search_hint))
        },
        leadingIcon = {
            IconButton(
                onClick = {
                    if (searchBarActive) {searchBarActive = false} else { popBackStack() }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.navigate_back)
                )
            }
        },
        trailingIcon = {
            if (searchBarActive){
                IconButton(onClick = { onValueChange("") }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = stringResource(
                            id = R.string.search_bar_clear_text_content_desc
                        )
                    )
                }
            }else {

                val interactionSource = remember { MutableInteractionSource() }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { onSearch(currentQuery())}) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null)
                    }

                    BadgedBox(
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) { changeFilters() },
                        badge = {
                            if (currentState.numOfFilters != 0){
                                Badge(
                                    containerColor = MaterialTheme.colorScheme.tertiary,
                                    contentColor = MaterialTheme.colorScheme.onTertiary,
                                ){
                                    Text(text = currentState.numOfFilters.toString())
                                }
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_filter ),
                            contentDescription = stringResource(
                                id = R.string.search_filter_icon_content_desc
                            )
                        )
                    }
                }

            }
        }

    ){

        SearchHistory(
            currentHistory = queryHistory,
            onClick = {
                onSearch(it)
                onValueChange(it)
                searchBarActive = false
            }
        )

    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SearchTabLayout(
    pagerState: PagerState,
    pageContent: @Composable (Int) -> Unit,
){

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        TabRow(
            selectedTabIndex = pagerState.currentPage
        ) {
            VisualMediaTypes.values().forEachIndexed { index, type ->

                Tab(
                    text = { Text(text = type.toString()) },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }

        HorizontalPager(
            pageCount = VisualMediaTypes.values().size,
            state = pagerState,
            beyondBoundsPageCount = 1
        ) {

            pageContent(it)

        }

    }

}

@Composable
private fun SearchAnimeContentList(
    basicAnimes: Flow<PagingData<BasicAnimeUi>>,
    onClick: (ReducedVisualMediaUi) -> Unit
){

    val animeData = basicAnimes.collectAsLazyPagingItems()
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
fun SearchMangaContentList(
    basicMangas : Flow<PagingData<BasicMangaUi>>,
    onClick: (ReducedVisualMediaUi) -> Unit
) {

    val mangaData = basicMangas.collectAsLazyPagingItems()
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



@Composable
private fun  SearchHistory(
    currentHistory : List<String>,
    onClick: (String) -> Unit
){

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ){

        items(currentHistory){

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick(it) }
                    .padding(16.dp)
            ) {
                Icon(painterResource(id = R.drawable.ic_history), contentDescription = null)
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = it
                )
            }

        }

    }

}

@Composable
private fun ShowSnackbarLaunchedEffect(
    snackBarState: Boolean,
    snackBarController: SnackBarController,
    snackbarShown: () -> Unit
){
    val snackbarMsg = stringResource(id = R.string.search_min_characters_query_error)

    LaunchedEffect(key1 = snackBarState){
        if (snackBarState){
            snackBarController.showSnackbar(
                snackbarType = SnackbarType.Error,
                message = snackbarMsg,
            )
            snackbarShown()
        }
    }
}

@Composable
private fun AddQueryToAnimeHistoryLaunchedEffect(
    animeQuery: String,
    addToHistory : (String) -> Unit
){
    LaunchedEffect(key1 = animeQuery){
        addToHistory(animeQuery)
    }
}

@Composable
private fun AddQueryToMangaHistoryLaunchedEffect(
    mangaQuery: String,
    addToHistory : (String) -> Unit
){
    LaunchedEffect(key1 = mangaQuery){
        addToHistory(mangaQuery)
    }
}

private fun addQueryToHistorySearch(newQuery : String, queryRecord : MutableList<String>){

    if (newQuery.length >= 3){

        val index = queryRecord.indexOf(newQuery)

        if (index != -1){

            queryRecord.removeAt(index)

        }

        queryRecord.add(0,newQuery)

    }

}

