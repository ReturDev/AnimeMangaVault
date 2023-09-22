package github.returdev.animemangavault.data.cache.entity;

sealed class VisualMediaCache{
    abstract val order : Long
    abstract val id : Int
    abstract val images : List<String>
    abstract val title : String
    abstract val score : Float
    abstract val type : String
    abstract val genres : List<String>
    abstract val demographics : List<String>
}
