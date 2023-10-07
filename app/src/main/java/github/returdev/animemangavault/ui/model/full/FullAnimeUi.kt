package github.returdev.animemangavault.ui.model.full


import github.returdev.animemangavault.core.model.components.ImageUrl
import github.returdev.animemangavault.core.model.components.Released
import github.returdev.animemangavault.core.model.components.Title
import github.returdev.animemangavault.ui.model.components.anime.AnimeDemographics
import github.returdev.animemangavault.ui.model.components.anime.AnimeStatus
import github.returdev.animemangavault.ui.model.components.anime.AnimeTypes
import github.returdev.animemangavault.ui.model.components.anime.Season
import github.returdev.animemangavault.ui.model.components.common.Genres

/**
 * Data class representing detailed information about an anime for UI purposes.
 *
 * @property id Unique identifier for the anime.
 * @property type The type of the anime (e.g., TV, OVA).
 * @property images List of image URLs associated with the anime.
 * @property titles List of titles associated with the anime.
 * @property score The score or rating of the anime.
 * @property numberOfScorers The number of users who have scored or rated the anime.
 * @property rank The rank of the anime.
 * @property synopsis A synopsis or summary of the anime.
 * @property genres List of genres associated with the anime.
 * @property demographics List of demographics or target audiences for the anime.
 * @property source The source material of the anime (e.g., manga, light novel).
 * @property episodes The number of episodes in the anime.
 * @property status The status of the anime (e.g., currently airing, finished airing).
 * @property airing Indicates whether the anime is currently airing.
 * @property aired Information about the aired dates of the anime.
 * @property season The season in which the anime is released (optional).
 */
data class FullAnimeUi(
    override val id: Int,
    override val type: AnimeTypes,
    override val images: List<ImageUrl>,
    override val titles: List<Title>,
    override val score: Float,
    override val numberOfScorers: Long,
    override val rank: Int,
    override val synopsis: String,
    override val status: AnimeStatus,
    override val genres: List<Genres>,
    override val demographics: List<AnimeDemographics>,
    val source: String,
    val episodes: Int,
    val airing: Boolean,
    val aired: Released,
    val season: Season?
) : FullVisualMediaUi()