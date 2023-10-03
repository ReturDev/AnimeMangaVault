package github.returdev.animemangavault.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import github.returdev.animemangavault.ui.navigation.Destination.DetailedItemScreen
import github.returdev.animemangavault.ui.navigation.Destination.NoArgumentsDestination

@Composable
fun NavigationGraph (
    navController: NavHostController,
    modifier : Modifier
) {

    NavHost(navController = navController, startDestination = NoArgumentsDestination.HomeScreen()){


        composable(destination = NoArgumentsDestination.HomeScreen){
            //TODO Introduce HomeScreen
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