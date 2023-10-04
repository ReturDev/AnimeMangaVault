package github.returdev.animemangavault.domain.model.full

import github.returdev.animemangavault.core.model.components.ImageUrl
import github.returdev.animemangavault.core.model.components.Released
import github.returdev.animemangavault.core.model.components.Title

/**
 * Data class representing detailed information about an anime.
 *
 * @param id The unique identifier of the anime.
 * @param type The type of the anime.
 * @param images The list of image URLs associated with the anime.
 * @param titles The list of titles for the anime.
 * @param score The score/rating of the anime.
 * @param numberOfScorers The number of users who have scored the anime.
 * @param rank The rank of the anime.
 * @param synopsis A brief synopsis of the anime.
 * @param genres The list of genres associated with the anime.
 * @param demographics The list of demographics associated with the anime.
 * @param source The source of the anime.
 * @param episodes The number of episodes in the anime.
 * @param status The status of the anime (e.g., ongoing, completed).
 * @param airing Indicates if the anime is currently airing.
 * @param aired Information about when the anime aired.
 * @param season The season in which the anime was released.
 */
data class FullAnime (

    override val id: Int,
    override val type: String?,
    override val images: List<ImageUrl>,
    override val titles: List<Title>,
    override val score: Float,
    override val numberOfScorers: Long,
    override val rank: Int,
    override val synopsis: String,
    override val status: String,
    override val genres: List<Int>,
    override val demographics: List<Int>,
    val source: String,
    val episodes: Int,
    val airing: Boolean,
    val aired: Released,
    val season: String?

) : FullVisualMedia()