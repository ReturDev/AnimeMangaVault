package github.returdev.animemangavault.ui.core.composables

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import github.returdev.animemangavault.R
import github.returdev.animemangavault.ui.model.filters.library.UserLibraryVisualMediaStatesUi
import github.returdev.animemangavault.ui.theme.AnimeMangaVaultTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddToLibraryDialog(
    onCloseDialog : () -> Unit,
    currentState : UserLibraryVisualMediaStatesUi?,
    saveState : (UserLibraryVisualMediaStatesUi?) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState()
    val hideDialog : () -> Unit = {
        coroutineScope.launch {
            modalSheetState.hide()
            onCloseDialog()
        }
    }

    ModalBottomSheet(

        onDismissRequest = {onCloseDialog()},
        sheetState = modalSheetState,
        dragHandle = null
    ) {

        DialogBody(
            currentState = currentState,
            onCancel = hideDialog,
            onSave = {newState ->
                saveState(newState)
                hideDialog()
            }
        )

    }

}

@Composable
private fun DialogBody(
    currentState : UserLibraryVisualMediaStatesUi?,
    onCancel: () -> Unit,
    onSave: (UserLibraryVisualMediaStatesUi?) -> Unit
){

    var stateSelected by remember { mutableStateOf(currentState) }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {

        DialogButtons(onCancel = onCancel , onSave = { onSave(stateSelected) })

        DialogStateList(
            stateSelected = stateSelected,
            onSelectState = { newState ->
                stateSelected = if (stateSelected == newState) { null } else { newState}
            }
        )

        //Temporary fix until the bottom sheet bug with WindowInsets.navigationBars is resolved.
        Spacer(
            modifier = Modifier.height(
                with(LocalDensity.current) { (WindowInsets.navigationBars.getBottom(LocalDensity.current)/2).toDp() }
            )
        )

    }

}

@Composable
private fun DialogButtons(
    onCancel : () -> Unit,
    onSave : () -> Unit
) {

    ConstraintLayout(Modifier.fillMaxWidth()) {

        val (cancelCons, saveCons) = createRefs()

        TextButton(
            modifier = Modifier.constrainAs(cancelCons){
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            },
            onClick = { onCancel() }
        ) {
            Text(text = stringResource(id = R.string.cancel))
        }

        TextButton(
            modifier = Modifier.constrainAs(saveCons){
                end.linkTo(parent.end)
                top.linkTo(parent.top)
            },
            onClick = { onSave()}
        ) {
            Text(text = stringResource(id = R.string.save))
        }

    }
}

@Composable
private fun DialogStateList(
    stateSelected : UserLibraryVisualMediaStatesUi?,
    onSelectState : (UserLibraryVisualMediaStatesUi?) -> Unit
) {

    LazyColumn(
        contentPadding = PaddingValues(
            top = 16.dp,
            start = 8.dp,
            end = 8.dp,
            bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){

        items(UserLibraryVisualMediaStatesUi.values()){
            LibraryStateItem(isSelected = it == stateSelected, stateSelectedRes = it.stringResource){
                onSelectState(it)
            }
        }

    }

}


@Composable
private inline fun LibraryStateItem(
    isSelected : Boolean,
    @StringRes stateSelectedRes : Int,
    crossinline onSelectState : () -> Unit,
) {

    val backgroundColor : Color
    val contentColor : Color

    if (isSelected){
        backgroundColor = MaterialTheme.colorScheme.secondary
        contentColor = MaterialTheme.colorScheme.onSecondary
    }else{
        backgroundColor = MaterialTheme.colorScheme.secondaryContainer
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(25))
            .background(backgroundColor)
            .border(1.dp, shape = RoundedCornerShape(25), color = MaterialTheme.colorScheme.outline)
            .shadow(elevation = (-4).dp)
            .clickable { onSelectState() }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .defaultMinSize(minHeight = 24.dp),
            text = stringResource(id = stateSelectedRes),
            color = contentColor
        )
        AnimatedVisibility(visible = isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Checked",
                tint = contentColor
            )
        }
    }

}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun DialogPrev() {

    AnimeMangaVaultTheme {




    }

}