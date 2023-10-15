package github.returdev.animemangavault.ui.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import github.returdev.animemangavault.ui.core.snackbar.SnackBarController
import github.returdev.animemangavault.ui.screen.detailed.DetailedItemScreen
import github.returdev.animemangavault.ui.screen.home.HomeScreen
import github.returdev.animemangavault.ui.screen.library.LibraryScreen
import github.returdev.animemangavault.ui.screen.search.SearchScreen

@Composable
fun NavigationGraph (
    navController: NavHostController,
    snackBarController: SnackBarController,
    modifier : Modifier
) {

    NavHost(navController = navController, startDestination = Destination.NoArgumentsDestination.HomeScreenDestination()){


        composable(destination = Destination.NoArgumentsDestination.HomeScreenDestination){
            HomeScreen(modifier = modifier, navController = navController)
        }

        composable(destination = Destination.NoArgumentsDestination.LibraryScreenDestination){
            LibraryScreen(modifier = modifier, navController = navController)
        }

        composable(destination = Destination.NoArgumentsDestination.SearchScreenDestination){
            SearchScreen(navController = navController,  snackBarController = snackBarController)
        }
        composable(destination = Destination.DetailedItemScreenDestination){
            DetailedItemScreen(navController = navController, snackBarController = snackBarController, navEntry = it)
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