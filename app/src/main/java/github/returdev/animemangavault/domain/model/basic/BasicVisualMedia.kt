package github.returdev.animemangavault.domain.model.basic

import github.returdev.animemangavault.core.model.components.ImageUrl

/**
 * Sealed class representing basic information about visual media such as anime and manga.
 *
 * @property id Unique identifier for the visual media.
 * @property images List of image URLs associated with the visual media.
 * @property title The title of the visual media.
 * @property score The score or rating of the visual media.
 * @property type The type of the visual media.
 * @property genres List of genres associated with the visual media.
 * @property demographics List of demographics or target audiences for the visual media.
 */
sealed class BasicVisualMedia{
    abstract val id: Int
    abstract val images: List<ImageUrl>
    abstract val title: String
    abstract val score: Float
    abstract val type : String?
    abstract val genres : List<Int>
    abstract val demographics : List<Int>
}