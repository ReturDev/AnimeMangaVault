package github.returdev.animemangavault.ui.core.navigation

import androidx.navigation.NavHostController
import github.returdev.animemangavault.ui.model.filters.core.VisualMediaTypes
import github.returdev.animemangavault.ui.model.reduced.ReducedVisualMediaUi

fun NavHostController.navigateToItemDetails(reducedVisualMediaUi: ReducedVisualMediaUi){

    this.navigate(route = Destination.DetailedItemScreenDestination(
        reducedVisualMediaUi.id.toString(),
        reducedVisualMediaUi.defaultTitle,
        VisualMediaTypes.getType(reducedVisualMediaUi).name
    ))

}