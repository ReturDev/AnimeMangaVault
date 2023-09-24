package github.returdev.animemangavault.domain.model.full

import github.returdev.animemangavault.core.model.components.ImageUrl
import github.returdev.animemangavault.core.model.components.Released
import github.returdev.animemangavault.core.model.components.Title


/**
 * Data class representing detailed information about a manga.
 *
 * @param id The unique identifier of the manga.
 * @param type The type of the manga.
 * @param images The list of image URLs associated with the manga.
 * @param titles The list of titles for the manga.
 * @param score The score/rating of the manga.
 * @param numberOfScorers The number of users who have scored the manga.
 * @param rank The rank of the manga.
 * @param synopsis A brief synopsis of the manga.
 * @param genres The list of genres associated with the manga.
 * @param demographics The list of demographics associated with the manga.
 * @param chapters The number of chapters in the manga.
 * @param volumes The number of volumes of the manga.
 * @param publishing Indicates if the manga is currently being published.
 * @param published Information about when the manga was published.
 */
data class FullManga (
    override val id: Int,
    override val type: String?,
    override val images: List<ImageUrl>,
    override val titles: List<Title>,
    override val score: Float,
    override val numberOfScorers: Long,
    override val rank: Int,
    override val synopsis: String,
    override val genres: List<Int>,
    override val demographics: List<Int>,
    val chapters: Int,
    val volumes: Int,
    val publishing: Boolean,
    val published: Released
) : FullVisualMedia()