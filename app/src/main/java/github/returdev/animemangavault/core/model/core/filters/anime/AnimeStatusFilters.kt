package github.returdev.animemangavault.core.model.core.filters.anime

enum class AnimeStatusFilters {

    AIRING,
    COMPLETE,
    UPCOMING;

    override fun toString(): String {
        return super.toString().lowercase()
    }
}
