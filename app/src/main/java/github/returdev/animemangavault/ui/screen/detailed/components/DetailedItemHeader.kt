package github.returdev.animemangavault.ui.screen.detailed.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import github.returdev.animemangavault.R
import github.returdev.animemangavault.ui.model.filters.library.UserLibraryVisualMediaStatesUi
import github.returdev.animemangavault.ui.theme.AnimeMangaVaultTheme


@Composable
fun DetailedItemHeader(
    libraryVMState: UserLibraryVisualMediaStatesUi?,
    defaultTitle : String,
    navBack : () -> Unit,
    addToLibEnabled : Boolean,
    addToLib : () -> Unit
){
    Surface(
        modifier = Modifier.defaultMinSize(),
        color = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp),
        shadowElevation = 8.dp,
        shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            HeaderTop(
                libraryVMState = libraryVMState,
                clickNavBack = {navBack()},
                addToLibEnabled = addToLibEnabled,
                clickAddToLib = {addToLib()}
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = defaultTitle,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )

        }
    }

}


@Composable
private fun HeaderTop(
    modifier: Modifier = Modifier,
    libraryVMState: UserLibraryVisualMediaStatesUi?,
    clickNavBack : () -> Unit,
    addToLibEnabled : Boolean,
    clickAddToLib : () -> Unit
){

    val libraryIcon : Painter
    val libraryString : String

    if (libraryVMState == null){
        libraryIcon = painterResource(R.drawable.ic_add_to_library)
        libraryString = stringResource(R.string.btn_vm_detail_save_in_library)
    }else{
        libraryIcon = painterResource(R.drawable.ic_added_in_library)
        libraryString = stringResource(
            R.string.btn_vm_detail_saved,
            stringResource(id = libraryVMState.stringResource)
        )
    }

    ConstraintLayout(
        modifier = modifier.fillMaxWidth()
    ) {

        val (navBackCons, addToLibCons) = createRefs()

        IconButton(
            modifier = Modifier.constrainAs(navBackCons){
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            },
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = MaterialTheme.colorScheme.primary
            ),
            onClick = { clickNavBack() }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.navigate_back)
            )
        }

        TextButton(
            modifier = Modifier.constrainAs(addToLibCons){
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            },
            onClick = { clickAddToLib() },
            enabled = addToLibEnabled,
            colors = ButtonDefaults.textButtonColors(
                contentColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Icon(
                painter = libraryIcon,
                contentDescription = null
            )
            Text(text = libraryString)
        }

    }


}

@Preview
@Composable
fun PreviewC(){
    AnimeMangaVaultTheme {
        Column(Modifier.fillMaxSize()){
            DetailedItemHeader(
                libraryVMState = null,
                defaultTitle = "TTT",
                navBack = {},
                addToLibEnabled = true,
                addToLib = {}
            )
        }
    }
}