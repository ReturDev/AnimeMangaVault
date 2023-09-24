package github.returdev.animemangavault.ui.model.basic

import github.returdev.animemangavault.core.model.components.ImageUrl
import github.returdev.animemangavault.ui.model.components.anime.AnimeTypes
import github.returdev.animemangavault.ui.model.components.common.Demographics
import github.returdev.animemangavault.ui.model.components.common.Genres

/**
 * Data class representing basic information about anime for UI purposes.
 *
 * @property id Unique identifier for the anime.
 * @property images List of image URLs associated with the anime.
 * @property title The title of the anime.
 * @property score The score or rating of the anime.
 * @property type The type of the anime (e.g., TV, OVA).
 * @property genres List of genres associated with the anime.
 * @property demographics List of demographics or target audiences for the anime.
 */
data class BasicAnimeUi(
    override val id: Int,
    override val images: List<ImageUrl>,
    override val title: String,
    override val score: Float,
    override val type: AnimeTypes,
    override val genres: List<Genres>,
    override val demographics: List<Demographics>
) : BasicVisualMediaUi()