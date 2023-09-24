package github.returdev.animemangavault.ui.model.full

import github.returdev.animemangavault.core.model.components.ImageUrl
import github.returdev.animemangavault.core.model.components.Released
import github.returdev.animemangavault.core.model.components.Title
import github.returdev.animemangavault.ui.model.components.common.Genres
import github.returdev.animemangavault.ui.model.components.manga.MangaDemographics

/**
 * Data class representing detailed information about a manga for UI purposes.
 *
 * @property id Unique identifier for the manga.
 * @property type The type of the manga.
 * @property images List of image URLs associated with the manga.
 * @property titles List of titles associated with the manga.
 * @property score The score or rating of the manga.
 * @property numberOfScorers The number of users who have scored or rated the manga.
 * @property rank The rank of the manga.
 * @property synopsis A synopsis or summary of the manga.
 * @property genres List of genres associated with the manga.
 * @property demographics List of demographics or target audiences for the manga.
 * @property chapters The number of chapters in the manga.
 * @property volumes The number of volumes in the manga.
 * @property publishing Indicates whether the manga is currently publishing.
 * @property published Information about the published dates of the manga.
 */
data class FullMangaUi(
    override val id: Int,
    override val type: Any,
    override val images: List<ImageUrl>,
    override val titles: List<Title>,
    override val score: Float,
    override val numberOfScorers: Long,
    override val rank: Int,
    override val synopsis: String,
    override val genres: List<Genres>,
    override val demographics: List<MangaDemographics>,
    val chapters: Int,
    val volumes: Int,
    val publishing: Boolean,
    val published: Released
) : FullVisualMediaUi()