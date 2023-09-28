package github.returdev.animemangavault.core.model.core.filters.manga

enum class MangaOrderByFilters {

    TITLE,
    START_DATE,
    END_DATE,
    CHAPTERS,
    VOLUMES,
    SCORE,
    SCORED_BY,
    RANK,
    POPULARITY,
    MEMBERS,
    FAVORITES;

    override fun toString(): String {
        return super.toString().lowercase()
    }

}
