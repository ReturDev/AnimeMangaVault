package github.returdev.animemangavault.ui.model.filters.search

import github.returdev.animemangavault.R

/**
 * Enum class representing different properties for ordering anime.
 *
 * @property stringResource The string resource ID for the corresponding property name.
 */
enum class AnimeOrderByProperties(val stringResource : Int) {
    TITLE(R.string.order_title),
    START_DATE(R.string.order_start_date),
    END_DATE(R.string.order_end_date),
    EPISODES(R.string.anime_order_episodes),
    SCORE(R.string.order_score),
    SCORED_BY(R.string.order_scored_by),
    RANK(R.string.order_rank),
    POPULARITY(R.string.order_popularity),
    FAVORITES(R.string.order_favorites)

}