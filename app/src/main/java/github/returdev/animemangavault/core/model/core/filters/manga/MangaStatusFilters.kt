package github.returdev.animemangavault.core.model.core.filters.manga

enum class MangaStatusFilters {

    PUBLISHING,
    COMPLETE,
    HIATUS,
    DISCONTINUED,
    UPCOMING;

    override fun toString(): String {
        return super.toString().lowercase()
    }

}
