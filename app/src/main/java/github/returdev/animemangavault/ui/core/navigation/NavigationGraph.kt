package github.returdev.animemangavault.ui.core.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import github.returdev.animemangavault.ui.screen.detailed.DetailedItemScreen
import github.returdev.animemangavault.ui.screen.home.HomeScreen
import github.returdev.animemangavault.ui.screen.library.LibraryScreen

@Composable
fun NavigationGraph (
    navController: NavHostController,
    snackBarHostState : SnackbarHostState,
    modifier : Modifier
) {

    NavHost(navController = navController, startDestination = Destination.NoArgumentsDestination.HomeScreenDirection()){


        composable(destination = Destination.NoArgumentsDestination.HomeScreenDirection){
            HomeScreen(modifier, navController, snackBarHostState)
        }

        composable(destination = Destination.NoArgumentsDestination.LibraryScreenDirection){
            LibraryScreen(modifier, navController, snackBarHostState)
        }

        composable(destination = Destination.NoArgumentsDestination.SearchScreenDirection){
            //TODO Introduce SearchScreen
        }
        composable(destination = Destination.DetailedItemScreenDestination){
            DetailedItemScreen(navController, snackBarHostState, it)
        }

    }


}

fun NavGraphBuilder.composable(
    destination: Destination,
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable (NavBackStackEntry) -> Unit
){

    composable(
        route = destination.fullRoute,
        arguments = destination.getArguments(),
        deepLinks = deepLinks,
        content = content
    )

}