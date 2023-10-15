package github.returdev.animemangavault.ui.core.navigation

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

        object HomeScreenDestination : NoArgumentsDestination("home")
        object LibraryScreenDestination : NoArgumentsDestination("library")
        object SearchScreenDestination : NoArgumentsDestination("search")

    }

    object DetailedItemScreenDestination : Destination(
        "detailed" ,"id", "title","vm_type"
    ) {

        const val ID = "id"
        const val TITLE = "title"
        const val VISUAL_MEDIA_TYPE = "vm_type"

        override fun getArguments(): List<NamedNavArgument> {
            return listOf(
                navArgument(name = ID){type= NavType.StringType},
                navArgument(name = TITLE){type= NavType.StringType},
                navArgument(name = VISUAL_MEDIA_TYPE){type= NavType.StringType}
            )
        }

        operator fun invoke(id : String, title : String, vmType : String ): String {
            return route.appendParams(id,title,vmType)
        }

    }

    protected fun String.appendParams(vararg params: Any?): String {
        val builder = StringBuilder(this)

        params.forEach {
            it?.toString()?.let { arg ->
                builder.append("/$arg")
            }
        }

        return builder.toString()
    }

}
