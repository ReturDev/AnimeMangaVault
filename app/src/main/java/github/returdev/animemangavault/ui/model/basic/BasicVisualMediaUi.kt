package github.returdev.animemangavault.ui.model.basic

import github.returdev.animemangavault.domain.model.components.ImageUrl
import github.returdev.animemangavault.ui.model.components.Demographics
import github.returdev.animemangavault.ui.model.components.Genres

/**
 * Sealed class representing basic information about visual media for UI purposes, such as anime and manga.
 *
 * @property id Unique identifier for the visual media.
 * @property images List of image URLs associated with the visual media.
 * @property title The title of the visual media.
 * @property score The score or rating of the visual media.
 * @property type The type of the visual media.
 * @property genres List of genres associated with the visual media.
 * @property demographics List of demographics or target audiences for the visual media.
 */
sealed class BasicVisualMediaUi{
    abstract val id: Int
    abstract val images: List<ImageUrl>
    abstract val title: String
    abstract val score: Float
    abstract val type : Any
    abstract val genres : List<Genres>
    abstract val demographics : List<Demographics>
}
