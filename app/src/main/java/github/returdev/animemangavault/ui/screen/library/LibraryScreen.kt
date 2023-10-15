package github.returdev.animemangavault.ui.screen.library

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import github.returdev.animemangavault.R
import github.returdev.animemangavault.ui.core.composables.items.ReducedItem
import github.returdev.animemangavault.ui.core.navigation.navigateToItemDetails
import github.returdev.animemangavault.ui.model.filters.core.SortDirectionUi
import github.returdev.animemangavault.ui.model.filters.core.VisualMediaTypes
import github.returdev.animemangavault.ui.model.filters.library.UserLibraryOrderByUi
import github.returdev.animemangavault.ui.model.filters.library.UserLibraryVisualMediaStatesUi
import github.returdev.animemangavault.ui.model.reduced.ReducedAnimeUi
import github.returdev.animemangavault.ui.model.reduced.ReducedMangaUi
import github.returdev.animemangavault.ui.model.reduced.ReducedVisualMediaUi
import kotlinx.coroutines.flow.Flow


@Suppress("UNCHECKED_CAST")
@Composable
fun LibraryScreen(
    modifier: Modifier,
    navController : NavHostController,
    viewModel: LibraryViewModel = hiltViewModel()
){

    val uiState by viewModel.uiState.collectAsState()

    Column(modifier.fillMaxSize()) {

        LibraryFiltersLayout(
            orderBySelected = uiState.currentOrderBy,
            sortDirectionSelected = uiState.currentSortDirection,
            onChangeOrderBy = viewModel.changeOrderBy,
            onClickSort = viewModel.changeSortDirection
        )

        LibraryVisualMediaTypeTabs(
            typeTabSelected = uiState.vmTypeSelected.ordinal,
            onTypeTabSelect = viewModel.changeVMType
        )

        when(uiState.vmTypeSelected){
            VisualMediaTypes.ANIME -> LibraryStatesTab(
                currentStateSelected = uiState.animeStateSelected,
                currentVMItems = viewModel.currentVMItems as Flow<PagingData<ReducedAnimeUi>>,
                onCLickElement = {vm -> navController.navigateToItemDetails(vm) },
                onChangeStateSelected = viewModel.changeAnimeStateSelected
            )
            VisualMediaTypes.MANGA -> LibraryStatesTab(
                currentStateSelected = uiState.mangaStateSelected,
                currentVMItems = viewModel.currentVMItems as Flow<PagingData<ReducedMangaUi>>,
                onCLickElement = {vm -> navController.navigateToItemDetails(vm) },
                onChangeStateSelected = viewModel.changeMangaStateSelected
            )
        }
    }
}

@Composable
private fun LibraryFiltersLayout(
    orderBySelected : UserLibraryOrderByUi,
    sortDirectionSelected : SortDirectionUi,
    onChangeOrderBy: (UserLibraryOrderByUi) -> Unit,
    onClickSort: () -> Unit
){

    var orderByMenuExpanded by remember { mutableStateOf(false) }

    ConstraintLayout(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
    ) {

        val (orderBy, sortDir) = createRefs()

        TextAndIconButton(
            modifier = Modifier
                .constrainAs(orderBy) {
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                },
            onClick = { orderByMenuExpanded = true },
            iconResource = R.drawable.ic_order_by,
            iconContentDescRes = R.string.order_by_icon_content_description,
            textResource = orderBySelected.stringResource
        )

                    OrderByMenu(
                        isExpanded = orderByMenuExpanded,
                        onDismiss = { orderByMenuExpanded = false },
                        onSelectItem = onChangeOrderBy
                    )

                    TextAndIconButton(
                        modifier = Modifier.constrainAs(sortDir) {
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        },
                        onClick = { onClickSort() },
                        iconResource = sortDirectionSelected.iconResource,
                        iconContentDescRes = R.string.sort_icon_content_description,
                        textResource = sortDirectionSelected.abbreviationResource
                    )

                }

}

@Composable
fun LibraryVisualMediaTypeTabs(
    typeTabSelected : Int,
    onTypeTabSelect : (VisualMediaTypes) -> Unit
){
    TabRow(
        selectedTabIndex = typeTabSelected
    ) {
        VisualMediaTypes.values().forEachIndexed { index, type ->
            Tab(
                text = { Text(text = type.toString())},
                selected = index == typeTabSelected,
                onClick = { onTypeTabSelect(type) })
        }
    }
}


@Composable
private fun <T : ReducedVisualMediaUi> LibraryStatesTab(
    currentStateSelected : UserLibraryVisualMediaStatesUi,
    currentVMItems : Flow<PagingData<T>>,
    onCLickElement: (ReducedVisualMediaUi) -> Unit,
    onChangeStateSelected : (UserLibraryVisualMediaStatesUi) -> Unit
) {

    ScrollableTabRow(
        selectedTabIndex = currentStateSelected.ordinal,
        edgePadding = 0.dp
    ) {

        UserLibraryVisualMediaStatesUi.values().forEachIndexed { _ , libraryVMState ->

            Tab(
                text = {
                    Text(text = stringResource(libraryVMState.stringResource))
                },
                selected = libraryVMState == currentStateSelected,
                onClick = { onChangeStateSelected(libraryVMState) },
            )

        }

    }


    LibraryList(
        visualMediaItems = currentVMItems,
        onCLickElement = onCLickElement
    )

}



@Composable
private fun <T:ReducedVisualMediaUi> LibraryList(
    visualMediaItems : Flow<PagingData<T>>,
    onCLickElement : (ReducedVisualMediaUi) -> Unit
){
        val stateItems = visualMediaItems.collectAsLazyPagingItems()

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        items(
            count = stateItems.itemCount,
            key = stateItems.itemKey{it.id}){index ->
            ReducedItem(reducedVisualMedia = stateItems[index]!!, onClick = { onCLickElement(stateItems[index]!!) })
        }
    }
}

@Composable
private fun TextAndIconButton(
    modifier: Modifier,
    onClick : () -> Unit,
    @DrawableRes iconResource : Int,
    @StringRes iconContentDescRes : Int,
    @StringRes textResource : Int
){

    TextButton(
        modifier = modifier,
        onClick = {onClick()}
    ){
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(iconResource),
                contentDescription = stringResource(iconContentDescRes)
            )
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = stringResource(textResource)
            )
        }
    }

}

@Composable
private fun OrderByMenu(
    isExpanded : Boolean,
    onDismiss : () -> Unit,
    onSelectItem : (UserLibraryOrderByUi) -> Unit
){

    DropdownMenu(
        expanded = isExpanded,
        onDismissRequest = { onDismiss() },
    ) {
        UserLibraryOrderByUi.values().forEach { orderBy ->
            DropdownMenuItem(
                text = { Text(text = stringResource(id = orderBy.stringResource)) },
                onClick = {
                    onSelectItem(orderBy)
                    onDismiss()
                }
            )
        }
    }
}

