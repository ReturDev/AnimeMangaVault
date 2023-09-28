package github.returdev.animemangavault.core.model.core.filters.anime

enum class AnimeTypeFilters {

    TV,
    MOVIE,
    OVA,
    SPECIAL,
    ONA,
    MUSIC;

    override fun toString(): String {
        return super.toString().lowercase()
    }

}
