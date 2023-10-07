package github.returdev.animemangavault.ui.screen.detailed

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import github.returdev.animemangavault.R
import github.returdev.animemangavault.ui.core.composables.AddToLibraryDialog
import github.returdev.animemangavault.ui.core.composables.ErrorIcon
import github.returdev.animemangavault.ui.core.navigation.Destination.DetailedItemScreenDestination
import github.returdev.animemangavault.ui.model.filters.library.UserLibraryVisualMediaStatesUi
import github.returdev.animemangavault.ui.model.full.FullVisualMediaUi
import github.returdev.animemangavault.ui.screen.detailed.components.DetailedItemAlternativeTitles
import github.returdev.animemangavault.ui.screen.detailed.components.DetailedItemHeader
import github.returdev.animemangavault.ui.screen.detailed.components.DetailedItemInformation
import github.returdev.animemangavault.ui.screen.detailed.components.DetailedItemSynopsis
import github.returdev.animemangavault.ui.theme.AnimeMangaVaultTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private const val ID = "id"
private const val TITLE = "title"

@Composable
fun DetailedItemScreen(
    navController: NavHostController,
    snackBarHostState : SnackbarHostState,
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
            snackBarHostState = snackBarHostState
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
                is DetailedItemUiState.Error -> DetailItemError(
                    it.errorResource,it.isRetryAvailable
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
fun DetailItemError(
    @StringRes errorMessageRes : Int,
    showRetryButton : Boolean,
    retry : () -> Unit
) {

    ConstraintLayout(Modifier.fillMaxSize()) {

        val columCons = createRef()

        Column(
            Modifier.constrainAs(columCons){
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ErrorIcon(
                Modifier.fillMaxWidth(0.3f).aspectRatio(1f)
            )

            Text(
                modifier = Modifier
                    .padding(top = 8.dp),
                text = stringResource(id = errorMessageRes),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.error
            )

            if (showRetryButton){

                Button(
                    modifier = Modifier.padding(top = 20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer
                    ),
                    onClick = { retry() }
                ) {

                    Icon(
                        modifier= Modifier.padding(4.dp),
                        imageVector = Icons.Default.Refresh,
                        contentDescription = null
                    )
                    Text(text = stringResource(id = R.string.retry))

                }

            }

        }


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
    snackBarHostState: SnackbarHostState,
    messageShown: () -> Unit,
){

    val messageString = if (stateInLibrary == null){
        stringResource(id = messageRes)
    }else {
        stringResource(id = messageRes, stringResource(id = stateInLibrary.stringResource))
    }

    showSnackBar(
        messageString,
        messageShown,
        snackBarHostState,
        coroutineScope
    )

}

private fun showSnackBar(
    message : String,
    messageShown: () -> Unit,
    snackBarHostState : SnackbarHostState,
    coroutineScope: CoroutineScope
){

    coroutineScope.launch {

        snackBarHostState.showSnackbar(message = message, duration = SnackbarDuration.Short)

        messageShown()

    }

}


@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PrevError() {

    AnimeMangaVaultTheme {

        Column(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)) {

            DetailItemError(
                R.string.generic_error,
                true,
                {}
            )

        }

    }

}