package github.returdev.animemangavault.ui.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import github.returdev.animemangavault.ui.core.navigation.Destination.DetailedItemScreen
import github.returdev.animemangavault.ui.core.navigation.Destination.NoArgumentsDestination
import github.returdev.animemangavault.ui.screen.home.HomeScreen

@Composable
fun NavigationGraph (
    navController: NavHostController,
    modifier : Modifier
) {

    NavHost(navController = navController, startDestination = NoArgumentsDestination.HomeScreen()){


        composable(destination = NoArgumentsDestination.HomeScreen){
            HomeScreen(modifier, navController)
        }

        composable(destination = NoArgumentsDestination.LibraryScreen){
            //TODO Introduce LibraryScreen
        }

        composable(destination = NoArgumentsDestination.SearchScreen){
            //TODO Introduce SearchScreen
        }
        composable(destination = DetailedItemScreen){
            //TODO Introduce DetailedItemScreen
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