package github.returdev.animemangavault.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Destination(protected val route : String, vararg params : String){

    val fullRoute = if (params.isEmpty()) route else {
        buildString {
            append(route)
            params.forEach {
                append("/{$it}")
            }
        }
    }

    abstract fun getArguments() : List<NamedNavArgument>

    sealed class NoArgumentsDestination(route: String) : Destination(route){

        override fun getArguments(): List<NamedNavArgument> = emptyList()

        operator fun invoke() : String = route

        object HomeScreen : NoArgumentsDestination("home")
        object LibraryScreen : NoArgumentsDestination("library")
        object SearchScreen : NoArgumentsDestination("search")

    }

    sealed class ArgumentsDestination(route: String) : Destination(route)

    object DetailedItemScreen : Destination(
        "detailed" ,
        "id", "title", "type_resource", "rank", "vm_type"
    ) {

        override fun getArguments(): List<NamedNavArgument> {
            return listOf(
                navArgument(name = "id"){type= NavType.StringType},
                navArgument(name = "title"){type= NavType.StringType},
                navArgument(name = "type_resource"){type= NavType.IntType; defaultValue = -1},
                navArgument(name = "rank"){type= NavType.FloatType},
                navArgument(name = "vm_type"){type= NavType.StringType}
            )
        }

    }

}
