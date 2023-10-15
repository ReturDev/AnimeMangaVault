package github.returdev.animemangavault.ui.core.composables.bottomsheet

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import github.returdev.animemangavault.R

@Composable
fun BottomSheetTopButtons(
    onClickStartBtn : () -> Unit,
    onClickEndBtn : () -> Unit,
    @StringRes startBtnTextRes : Int = R.string.cancel,
    @StringRes endBtnTextRes : Int = R.string.save

    ) {

    ConstraintLayout(Modifier.fillMaxWidth()) {

        val (cancelCons, saveCons) = createRefs()

        TextButton(
            modifier = Modifier.constrainAs(cancelCons){
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            },
            onClick = { onClickStartBtn() }
        ) {
            Text(text = stringResource(id = startBtnTextRes))
        }

        TextButton(
            modifier = Modifier.constrainAs(saveCons){
                end.linkTo(parent.end)
                top.linkTo(parent.top)
            },
            onClick = { onClickEndBtn()}
        ) {
            Text(text = stringResource(id = endBtnTextRes))
        }

    }

}

@Composable
fun BottomSpacerBottomSheet() {
    //Temporary fix until the bottom sheet bug with WindowInsets.navigationBars is resolved.
    Spacer(
        modifier = Modifier.height(
            with(LocalDensity.current) { (WindowInsets.navigationBars.getBottom(LocalDensity.current)/2).toDp() }
        )
    )
}