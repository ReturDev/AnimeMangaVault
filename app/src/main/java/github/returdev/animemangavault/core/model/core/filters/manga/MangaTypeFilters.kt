package github.returdev.animemangavault.core.model.core.filters.manga

enum class MangaTypeFilters {

    MANGA,
    NOVEL,
    LIGHT_NOVEL,
    ONESHOT,
    DOUJIN,
    MANHWA,
    MANHUA;

    override fun toString(): String {
        return super.toString().lowercase().replace("_", "")
    }

}
