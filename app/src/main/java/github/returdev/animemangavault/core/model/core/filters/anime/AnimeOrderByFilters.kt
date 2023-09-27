package github.returdev.animemangavault.core.model.core.filters.anime

enum class AnimeOrderByFilters {
    TITLE,
    START_DATE,
    END_DATE,
    EPISODES,
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
