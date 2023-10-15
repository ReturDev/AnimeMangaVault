package github.returdev.animemangavault.ui.screen.detailed

import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import github.returdev.animemangavault.R
import github.returdev.animemangavault.ui.core.composables.ErrorLayout
import github.returdev.animemangavault.ui.core.composables.bottomsheet.AddToLibraryDialog
import github.returdev.animemangavault.ui.core.navigation.Destination.DetailedItemScreenDestination
import github.returdev.animemangavault.ui.core.snackbar.SnackBarController
import github.returdev.animemangavault.ui.model.filters.library.UserLibraryVisualMediaStatesUi
import github.returdev.animemangavault.ui.model.full.FullVisualMediaUi
import github.returdev.animemangavault.ui.screen.detailed.components.DetailedItemAlternativeTitles
import github.returdev.animemangavault.ui.screen.detailed.components.DetailedItemHeader
import github.returdev.animemangavault.ui.screen.detailed.components.DetailedItemInformation
import github.returdev.animemangavault.ui.screen.detailed.components.DetailedItemSynopsis
import kotlinx.coroutines.CoroutineScope

private const val ID = "id"
private const val TITLE = "title"

@Composable
fun DetailedItemScreen(
    navController: NavHostController,
    snackBarController: SnackBarController,
    navEntry: NavBackStackEntry,
    viewModel: DetailedItemViewModel = hiltViewModel()
){

    val coroutineScope = rememberCoroutineScope()
    var showAddToLibraryDialog by remember { mutableStateOf(false) }
    val headerVMData : Map<String,String> = remember {

        val id = navEntry.arguments?.getString(DetailedItemScreenDestination.ID)!!
        val vmType = navEntry.arguments?.getString(DetailedItemScreenDestination.VISUAL_MEDIA_TYPE)!!
        viewModel.initData(id, vmType)
        mutableMapOf(
            ID to id,
            TITLE to navEntry.arguments?.getString(DetailedItemScreenDestination.TITLE)!!
        )

    }

    val uiState by viewModel.uiState.collectAsState()
    val inLibraryState by viewModel.inLibraryState.collectAsState()

    if (inLibraryState.message.showMessage){
        ChangedInLibrary(
            stateInLibrary = inLibraryState.state,
            messageRes = inLibraryState.message.messageRes,
            coroutineScope = coroutineScope,
            snackBarController = snackBarController
        ) { viewModel.messageShown() }
    }

    if (showAddToLibraryDialog){
        AddToLibraryDialog(
            onCloseDialog = {showAddToLibraryDialog = false},
            currentState = inLibraryState.state,
            saveState = { state -> viewModel.saveState(state) }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        DetailedItemHeader(
            inLibraryState.state,
            headerVMData[TITLE]!!.toString(),
            {navController.popBackStack()},
            uiState is DetailedItemUiState.Success,
            { showAddToLibraryDialog = true }
        )

        val crossfadeModifier = if (uiState is DetailedItemUiState.Success){
            Modifier.fillMaxSize()
        }else{
            Modifier.weight(1f)
        }
        Crossfade(modifier = crossfadeModifier, targetState = uiState, label = "crossfade") {
            when(it){
                is DetailedItemUiState.Error -> ErrorLayout(
                    modifier = Modifier.fillMaxSize(),
                    errorMessageRes = it.errorResource,
                    showRetryButton = it.isRetryAvailable
                ) { viewModel.reloadDetails() }
                DetailedItemUiState.Loading -> DetailItemLoading()
                is DetailedItemUiState.Success -> DetailItemSuccess(it.vmData)
            }
        }

    }
}

@Composable
private fun DetailItemSuccess (vm : FullVisualMediaUi){

    Column(Modifier.fillMaxWidth()) {
        DetailedItemContainer(containerTitleRes = R.string.vm_detail_information_label) {
            DetailedItemInformation(visualMediaUi = vm)
        }

        DetailedItemContainer(containerTitleRes = R.string.vm_detail_synopsis_label) {
            DetailedItemSynopsis(modifier = Modifier.align(Alignment.End), synopsis = vm.synopsis)
        }

        DetailedItemContainer(containerTitleRes = R.string.vm_detail_alternative_titles) {
            DetailedItemAlternativeTitles(titles = vm.titles)
        }
    }

}

@Composable
private fun DetailItemLoading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        CircularProgressIndicator()
    }
}



@Composable
private fun DetailedItemContainer(
    @StringRes containerTitleRes: Int,
    content: @Composable (ColumnScope.() -> Unit)
) {

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ){

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ){
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = stringResource(id = containerTitleRes),
                style = MaterialTheme.typography.titleLarge
            )
            content()
        }

    }

}

@Composable
private fun ChangedInLibrary(
    stateInLibrary: UserLibraryVisualMediaStatesUi?,
    @StringRes messageRes : Int,
    coroutineScope: CoroutineScope,
    snackBarController: SnackBarController,
    messageShown: () -> Unit,
){

    val messageString = if (stateInLibrary == null){
        stringResource(id = messageRes)
    }else {
        stringResource(id = messageRes, stringResource(id = stateInLibrary.stringResource))
    }

    snackBarController.showSnackbar(
        coroutineScope = coroutineScope,
        message = messageString,
        executeAfter = messageShown
    )

}