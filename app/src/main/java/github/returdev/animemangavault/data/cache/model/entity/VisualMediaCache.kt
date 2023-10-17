package github.returdev.animemangavault.data.cache.model.entity;

sealed class VisualMediaCache{
    abstract val order : Long
    abstract val id : Int
    abstract val images : List<String>
    abstract val title : String
    abstract val score : Float
    abstract val type : String
    abstract val genres : List<Int>
    abstract val demographics : List<Int>
}
