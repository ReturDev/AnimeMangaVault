package github.returdev.animemangavault.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun AMVNavigation () {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.HomeScreen.route){

        composable(route = Routes.HomeScreen.route){
            //TODO Introduce HomeScreen
        }
        composable(route = Routes.LibraryScreen.route){
            //TODO Introduce LibraryScreen
        }
        composable(route = Routes.SearchScreen.route){
            //TODO Introduce SearchScreen
        }
        composable(
            route = Routes.DetailedItemScreen.getRouteWithArgs(),
            arguments = Routes.DetailedItemScreen.args.map {(argName,argType) ->
                navArgument(name = argName) {
                    type = NavType.inferFromValue(argType)
                    nullable = argName == "type_resource"
                }
            }
        ){

        }


    }


}