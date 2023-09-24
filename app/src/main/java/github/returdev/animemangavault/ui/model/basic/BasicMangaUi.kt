package github.returdev.animemangavault.ui.model.basic

import github.returdev.animemangavault.core.model.components.ImageUrl
import github.returdev.animemangavault.ui.model.components.common.Genres
import github.returdev.animemangavault.ui.model.components.manga.MangaDemographics
import github.returdev.animemangavault.ui.model.components.manga.MangaTypes

/**
 * Data class representing basic information about manga for UI purposes.
 *
 * @property id Unique identifier for the manga.
 * @property images List of image URLs associated with the manga.
 * @property title The title of the manga.
 * @property score The score or rating of the manga.
 * @property type The type of the manga (e.g., Manga, Novel).
 * @property genres List of genres associated with the manga.
 * @property demographics List of demographics or target audiences for the manga.
 */
data class BasicMangaUi(
    override val id: Int,
    override val images: List<ImageUrl>,
    override val title: String,
    override val score: Float,
    override val type: MangaTypes,
    override val genres: List<Genres>,
    override val demographics: List<MangaDemographics>
) : BasicVisualMediaUi()