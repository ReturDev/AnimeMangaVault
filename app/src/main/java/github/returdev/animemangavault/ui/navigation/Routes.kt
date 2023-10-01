package github.returdev.animemangavault.ui.navigation

import androidx.navigation.NavType

sealed class Routes(val route : String, val args : Map<String,String>){
    object HomeScreen : Routes("home_screen", emptyMap())
    object LibraryScreen : Routes("library_screen", emptyMap())
    object SearchScreen : Routes("search_screen", emptyMap())
    object DetailedItemScreen : Routes(
        "detailed_item_screen", mapOf(
            Pair("title", NavType.StringType.name),
            Pair("type_resource", NavType.IntType.name),
            Pair("rank", NavType.IntType.name),
            Pair("id", NavType.IntType.name),
            Pair("vm_type", NavType.StringType.name)
        )
    )

    fun getRouteWithArgs() : String{

        return buildString {
            append(route)
            args.keys.forEach{ arg->
                append("/$arg")
            }
        }

    }

}
