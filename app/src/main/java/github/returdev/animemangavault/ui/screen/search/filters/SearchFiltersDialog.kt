package github.returdev.animemangavault.ui.screen.search.filters


import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import github.returdev.animemangavault.R
import github.returdev.animemangavault.ui.core.composables.bottomsheet.BottomSheetTopButtons
import github.returdev.animemangavault.ui.core.composables.bottomsheet.BottomSpacerBottomSheet
import github.returdev.animemangavault.ui.model.components.anime.AnimeStatus
import github.returdev.animemangavault.ui.model.components.anime.AnimeTypes
import github.returdev.animemangavault.ui.model.components.common.Genres
import github.returdev.animemangavault.ui.model.components.manga.MangaStatus
import github.returdev.animemangavault.ui.model.components.manga.MangaTypes
import github.returdev.animemangavault.ui.model.filters.core.SortDirectionUi
import github.returdev.animemangavault.ui.model.filters.search.AnimeOrderByUi
import github.returdev.animemangavault.ui.model.filters.search.MangaOrderByUi
import github.returdev.animemangavault.ui.model.filters.search.SearchFiltersUi
import github.returdev.animemangavault.ui.theme.AnimeMangaVaultTheme
import kotlinx.coroutines.launch


private const val TYPE_LIST_INDEX = 0
private const val STATUS_LIST_INDEX = 1
private const val GENRES_LIST_INDEX = 2
private const val ORDER_BY_LIST_INDEX = 3

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchFiltersDialog(
    onCloseDialog : () -> Unit,
    currentFilters : SearchFiltersUi,
    saveFilters : (SearchFiltersUi) -> Unit,
) {

    val coroutineScope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val hideDialog : () -> Unit = {

        coroutineScope.launch {
            modalSheetState.hide()
            onCloseDialog()
        }

    }
    var newFilters by remember { mutableStateOf(currentFilters) }

    ModalBottomSheet(
        onDismissRequest = { onCloseDialog() },
        sheetState = modalSheetState
    ) {

        SearchFilterBody(
            onClearFilters = {
                newFilters = when (currentFilters) {
                    is SearchFiltersUi.AnimeFiltersUi -> SearchFiltersUi.AnimeFiltersUi()
                    is SearchFiltersUi.MangaFiltersUi -> SearchFiltersUi.MangaFiltersUi()
                }
            },
            onSave = {
                saveFilters(newFilters)
                hideDialog()
            },
            currentFilters = newFilters,
            updateCurrentFilters = { newFilters = it }
        )

        BottomSpacerBottomSheet()

    }

}

@Composable
private fun SearchFilterBody(
    onClearFilters : () -> Unit,
    onSave : () -> Unit,
    currentFilters: SearchFiltersUi,
    updateCurrentFilters: (SearchFiltersUi) -> Unit
){

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {

        BottomSheetTopButtons(
            onClickStartBtn = { onClearFilters() },
            onClickEndBtn = { onSave() },
            startBtnTextRes = R.string.clear_filters
        )

        Spacer(modifier = Modifier.height(16.dp))

        when(currentFilters){
            is SearchFiltersUi.AnimeFiltersUi -> SearchAnimeFilters(
                currentFilters = currentFilters,
                updateCurrentFilters = updateCurrentFilters
            )
            is SearchFiltersUi.MangaFiltersUi -> SearchMangaFilters(
                currentFilters = currentFilters,
                updateCurrentFilters = updateCurrentFilters
            )
        }

        Spacer(modifier = Modifier.height(50.dp))

    }

}

@Composable
fun SearchAnimeFilters(
    currentFilters : SearchFiltersUi.AnimeFiltersUi,
    updateCurrentFilters : (SearchFiltersUi) -> Unit
) {

    var currentListExpandedIndex by remember { mutableStateOf(-1) }
    val changeListExpandedIndex : (Int) -> Unit = { index ->
        currentListExpandedIndex = if (index == currentListExpandedIndex) {
            -1
        } else {
            index
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        AnimeTypeFilterList(
            currentAnimeTypeSelected = currentFilters.type,
            currentListExpandedIndex = currentListExpandedIndex,
            changeListExpandedIndex = { index -> changeListExpandedIndex(index)},
            updateAnimeTypeSelected = { animeTypes ->
                updateCurrentFilters(
                    currentFilters.copy(
                        type = if (animeTypes == currentFilters.type) null else animeTypes
                    )
                )
            }
        )


        AnimeStatusFilterList(
            currentAnimeStatusSelected = currentFilters.status,
            currentListExpandedIndex = currentListExpandedIndex,
            changeListExpandedIndex = { index -> changeListExpandedIndex(index)} ,
            updateAnimeStatusSelected = { mangaStatus ->
                updateCurrentFilters(
                    currentFilters.copy(
                        status = if (mangaStatus == currentFilters.status) null else mangaStatus
                    )
                )
            }
        )

        GenreFilterList(
            genresSelected = currentFilters.genres,
            currentListExpandedIndex = currentListExpandedIndex,
            changeListExpandedIndex = { index -> changeListExpandedIndex(index) },
            updateGenresSelected = { genre ->

                val genres = currentFilters.genres

                updateCurrentFilters(
                    currentFilters.copy(
                        genres = if (genres.contains(genre)){
                            genres.minus(genre)
                        }else{
                            genres.plus(genre)
                        }
                    )
                )

            }
        )

        AnimeOrderByFilterList(
            currentAnimeOrderBySelected = currentFilters.orderBy,
            currentListExpandedIndex = currentListExpandedIndex,
            changeListExpandedIndex = { index -> changeListExpandedIndex(index)},
            updateAnimeOrderBySelected = { orderBy ->
                updateCurrentFilters(
                    currentFilters.copy(
                        orderBy = if (orderBy == currentFilters.orderBy) null else orderBy
                    )
                )
            }
        )

        SearchSortButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            currentSortDir = currentFilters.sort,
            updateSortDir = { sortDir ->
                updateCurrentFilters(
                    currentFilters.copy(
                        sort = sortDir
                    )
                )
            }
        )

    }

}

@Composable
private fun AnimeTypeFilterList(
    currentAnimeTypeSelected : AnimeTypes?,
    currentListExpandedIndex : Int,
    changeListExpandedIndex : (Int) -> Unit,
    updateAnimeTypeSelected : (AnimeTypes) -> Unit
){

    ExpandableList(
        listIndex = TYPE_LIST_INDEX,
        currentListExpandedIndex = currentListExpandedIndex,
        listTitleRes = R.string.filter_type,
        changeListExpandedIndex = changeListExpandedIndex,
        selectedFilterResList = currentAnimeTypeSelected?.let { listOf(it.stringResource) } ?: emptyList()
    ) {

        FilterChipContainer {

            AnimeTypes.values().filter { it != AnimeTypes.UNKNOWN }.forEach { mangaType ->

                FilterChip(
                    selected = mangaType == currentAnimeTypeSelected,
                    onClick = { updateAnimeTypeSelected(mangaType) },
                    labelRes = mangaType.stringResource
                )

            }

        }

    }

}

@Composable
fun AnimeStatusFilterList(
    currentAnimeStatusSelected : AnimeStatus?,
    currentListExpandedIndex : Int,
    changeListExpandedIndex : (Int) -> Unit,
    updateAnimeStatusSelected : (AnimeStatus) -> Unit
) {

    ExpandableList(
        listIndex = STATUS_LIST_INDEX,
        currentListExpandedIndex = currentListExpandedIndex,
        listTitleRes = R.string.filter_status,
        selectedFilterResList = currentAnimeStatusSelected?.let{ listOf(it.stringResource) } ?: emptyList(),
        changeListExpandedIndex = changeListExpandedIndex
    ) {

        FilterChipContainer {

            AnimeStatus.values().forEach { animeStatus ->

                FilterChip(
                    selected = currentAnimeStatusSelected == animeStatus,
                    onClick = { updateAnimeStatusSelected(animeStatus) },
                    labelRes =  animeStatus.stringResource
                )

            }

        }

    }

}

@Composable
fun AnimeOrderByFilterList(
    currentAnimeOrderBySelected : AnimeOrderByUi?,
    currentListExpandedIndex : Int,
    changeListExpandedIndex : (Int) -> Unit,
    updateAnimeOrderBySelected : (AnimeOrderByUi) -> Unit
) {

    ExpandableList(
        listIndex = ORDER_BY_LIST_INDEX,
        currentListExpandedIndex = currentListExpandedIndex,
        listTitleRes = R.string.filter_order_by,
        selectedFilterResList = currentAnimeOrderBySelected?.let{ listOf(it.stringResource) } ?: emptyList(),
        changeListExpandedIndex = changeListExpandedIndex
    ) {

        FilterChipContainer {

            AnimeOrderByUi.values().forEach { orderBy ->

                FilterChip(
                    selected = currentAnimeOrderBySelected == orderBy,
                    onClick = { updateAnimeOrderBySelected(orderBy) },
                    labelRes = orderBy.stringResource
                )

            }

        }

    }

}


@Composable
fun SearchMangaFilters(
    currentFilters : SearchFiltersUi.MangaFiltersUi,
    updateCurrentFilters : (SearchFiltersUi) -> Unit
) {

    var currentListExpandedIndex by remember { mutableStateOf(-1) }
    val changeListExpandedIndex : (Int) -> Unit = { index ->
        currentListExpandedIndex = if (index == currentListExpandedIndex) {
            -1
        } else {
            index
        }
    }
    
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        
        MangaTypeFilterList(
            currentMangaTypeSelected = currentFilters.type,
            currentListExpandedIndex = currentListExpandedIndex,
            changeListExpandedIndex = { index -> changeListExpandedIndex(index)},
            updateMangaTypeSelected = { mangaType -> 
                updateCurrentFilters(
                    currentFilters.copy(
                        type = if (mangaType == currentFilters.type) null else mangaType
                    )
                )
            }
        )
        
        MangaStatusFilterList(
            currentMangaStatusSelected = currentFilters.status,
            currentListExpandedIndex = currentListExpandedIndex,
            changeListExpandedIndex = { index -> changeListExpandedIndex(index)} ,
            updateMangaStatusSelected = { mangaStatus ->
                updateCurrentFilters(
                    currentFilters.copy(
                        status = if (mangaStatus == currentFilters.status) null else mangaStatus
                    )
                )
            }
        )
        
        GenreFilterList(
            genresSelected = currentFilters.genres,
            currentListExpandedIndex = currentListExpandedIndex,
            changeListExpandedIndex = { index -> changeListExpandedIndex(index) },
            updateGenresSelected = { genre -> 
                
                val genres = currentFilters.genres
                
                updateCurrentFilters(
                    currentFilters.copy(
                        genres = if (genres.contains(genre)){
                            genres.minus(genre)
                        }else{
                            genres.plus(genre)
                        }
                    )
                )
                
            }
        )
        
        MangaOrderByFilterList(
            currentMangaOrderBySelected = currentFilters.orderBy,
            currentListExpandedIndex = currentListExpandedIndex,
            changeListExpandedIndex = { index -> changeListExpandedIndex(index)},
            updateMangaOrderBySelected = { orderBy ->
                updateCurrentFilters(
                    currentFilters.copy(
                        orderBy = if (orderBy == currentFilters.orderBy) null else orderBy
                    )
                )
            }
        )

        SearchSortButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            currentSortDir = currentFilters.sort,
            updateSortDir = { sortDir ->
                updateCurrentFilters(
                    currentFilters.copy(
                        sort = sortDir
                    )
                )
            }
        )
        
    }

}

@Composable
private fun MangaTypeFilterList(
    currentMangaTypeSelected : MangaTypes?,
    currentListExpandedIndex : Int,
    changeListExpandedIndex : (Int) -> Unit,
    updateMangaTypeSelected : (MangaTypes) -> Unit
){
    
    ExpandableList(
        listIndex = TYPE_LIST_INDEX,
        currentListExpandedIndex = currentListExpandedIndex,
        listTitleRes = R.string.filter_type,
        changeListExpandedIndex = changeListExpandedIndex,
        selectedFilterResList = currentMangaTypeSelected?.let { listOf(it.stringResource) } ?: emptyList()
    ) {

        FilterChipContainer {

            MangaTypes.values().filter { it != MangaTypes.UNKNOWN }.forEach { mangaType ->

                FilterChip(
                    selected = mangaType == currentMangaTypeSelected,
                    onClick = { updateMangaTypeSelected(mangaType) },
                    labelRes = mangaType.stringResource
                )

            }

        }

    }
    
}

@Composable
fun MangaStatusFilterList(
    currentMangaStatusSelected : MangaStatus?,
    currentListExpandedIndex : Int,
    changeListExpandedIndex : (Int) -> Unit,
    updateMangaStatusSelected : (MangaStatus) -> Unit
) {

    ExpandableList(
        listIndex = STATUS_LIST_INDEX,
        currentListExpandedIndex = currentListExpandedIndex,
        listTitleRes = R.string.filter_status,
        selectedFilterResList = currentMangaStatusSelected?.let{ listOf(it.stringResource) } ?: emptyList(),
        changeListExpandedIndex = changeListExpandedIndex
    ) {

        FilterChipContainer {

            MangaStatus.values().forEach { mangaStatus ->  
                
                FilterChip(
                    selected = currentMangaStatusSelected == mangaStatus,
                    onClick = { updateMangaStatusSelected(mangaStatus) },
                    labelRes =  mangaStatus.stringResource
                )
                
            }

        }

    }

}

@Composable
fun MangaOrderByFilterList(
    currentMangaOrderBySelected : MangaOrderByUi?,
    currentListExpandedIndex : Int,
    changeListExpandedIndex : (Int) -> Unit,
    updateMangaOrderBySelected : (MangaOrderByUi) -> Unit
) {
    
    ExpandableList(
        listIndex = ORDER_BY_LIST_INDEX,
        currentListExpandedIndex = currentListExpandedIndex,
        listTitleRes = R.string.filter_order_by,
        selectedFilterResList = currentMangaOrderBySelected?.let{ listOf(it.stringResource) } ?: emptyList(),
        changeListExpandedIndex = changeListExpandedIndex
    ) {
        
        FilterChipContainer {
            
            MangaOrderByUi.values().forEach { orderBy ->

                FilterChip(
                    selected = currentMangaOrderBySelected == orderBy,
                    onClick = { updateMangaOrderBySelected(orderBy) },
                    labelRes = orderBy.stringResource
                )
                
            }
            
        }
        
    }
    
}

@Composable
private fun ExpandableList(
    modifier : Modifier = Modifier,
    listIndex : Int,
    currentListExpandedIndex : Int,
    @StringRes listTitleRes : Int,
    @StringRes selectedFilterResList : List<Int>,
    changeListExpandedIndex : (Int) -> Unit,
    listContent : @Composable () -> Unit
) {

    val isExpanded = listIndex == currentListExpandedIndex

    val transition = updateTransition(targetState = isExpanded, label = "transition")
    val iconRotationDeg by transition.animateFloat(label = "icon_change") {state ->
        if (state){
            180f
        }else {
            0f
        }
    }

    val backgroundColor : Color
    val contentColor : Color

    if (isExpanded) {
        backgroundColor = MaterialTheme.colorScheme.secondary
        contentColor = MaterialTheme.colorScheme.onSecondary
    }else{
        backgroundColor = MaterialTheme.colorScheme.secondaryContainer
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 20f, topEnd = 20f))
                .background(backgroundColor)
                .clickable { changeListExpandedIndex(listIndex) }
                .padding(vertical = 8.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(2f),
                text = stringResource(id = listTitleRes),
                fontWeight = FontWeight.Bold,
                color = contentColor
            )
            if (!isExpanded){

                Text(
                    modifier = Modifier.weight(1f),
                    text = selectedFilterResList.map{ stringResource(id = it)}.joinToString(", "),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
            Icon(
                modifier = Modifier.rotate(iconRotationDeg),
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = stringResource(
                    id = if (isExpanded) R.string.arrow_drop_down_content_desc
                    else R.string.arrow_drop_up_content_desc
                ),
                tint = contentColor
            )
        }

        if (isExpanded){
            listContent()
        }

    }

}


@Composable
private fun GenreFilterList(
    genresSelected : List<Genres>,
    currentListExpandedIndex : Int,
    changeListExpandedIndex : (Int) -> Unit,
    updateGenresSelected : (Genres) -> Unit
) {

    ExpandableList(
        listIndex = GENRES_LIST_INDEX,
        currentListExpandedIndex = currentListExpandedIndex,
        listTitleRes = R.string.filter_genres,
        selectedFilterResList = genresSelected.map{ it.stringResource },
        changeListExpandedIndex = changeListExpandedIndex
    ) {

        val filterGenres by remember {
            mutableStateOf(Genres.values().filter { it != Genres.MANGA_SUSPENSE }
            )
        }
        
        FilterChipContainer {

            filterGenres.forEach { genre ->

                val isSelected = genresSelected.contains(genre)

                FilterChip(
                    selected = isSelected,
                    onClick = { updateGenresSelected(genre) },
                    labelRes = genre.stringResource
                )

            }

        }
        
    }

}


@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun FilterChipContainer(
    chips : @Composable () -> Unit
) {
    
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 20f, bottomEnd = 20f))
            .background(MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f))
            .padding(horizontal = 8.dp)
        ,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        chips()
    }
    
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private inline fun FilterChip(
    selected : Boolean,
    crossinline onClick : () -> Unit,
    @StringRes labelRes : Int
){

    ElevatedFilterChip(
        selected = selected ,
        onClick = { onClick() },
        colors = FilterChipDefaults.elevatedFilterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            selectedLabelColor = MaterialTheme.colorScheme.onTertiaryContainer
        ),
        label = {
            Text(text = stringResource(id = labelRes))
        }
    )

}


@Composable
private fun SearchSortButton(
    modifier: Modifier = Modifier,
    currentSortDir : SortDirectionUi,
    updateSortDir : (SortDirectionUi) -> Unit
){

    TextButton(
        modifier = modifier.fillMaxWidth(0.5f),
        shape = RoundedCornerShape(20),
        onClick = { updateSortDir(changeSortDirection(currentSortDir)) },
        colors = ButtonDefaults.textButtonColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
    ) {
        Icon(painter = painterResource(id = currentSortDir.iconResource), contentDescription = null)
        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = stringResource(id = currentSortDir.stringResource))
    }

}


private fun changeSortDirection(sortDirectionUi: SortDirectionUi): SortDirectionUi {

    return when(sortDirectionUi){
        SortDirectionUi.ASCENDANT -> SortDirectionUi.DESCENDANT
        SortDirectionUi.DESCENDANT -> SortDirectionUi.ASCENDANT
    }

}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showSystemUi = true
)
@Composable
fun PrevSS() {

    AnimeMangaVaultTheme {


        Column(Modifier.fillMaxSize()) {
            SearchFiltersDialog(
                onCloseDialog = { },
                currentFilters = SearchFiltersUi.AnimeFiltersUi(),
                saveFilters = {}
            )
        }

    }

}