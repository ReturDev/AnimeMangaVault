package github.returdev.animemangavault.domain.model.full

import github.returdev.animemangavault.core.model.components.ImageUrl
import github.returdev.animemangavault.core.model.components.Title

/**
 * Sealed class representing detailed information about visual media items such as anime and manga.
 *
 * @property id Unique identifier for the visual media item.
 * @property type The type of the visual media item (e.g., "anime" or "manga").
 * @property images List of image URLs associated with the visual media item.
 * @property titles List of titles associated with the visual media item.
 * @property score The score or rating of the visual media item.
 * @property numberOfScorers The number of users who have scored or rated the visual media item.
 * @property rank The rank of the visual media item.
 * @property synopsis A synopsis or summary of the visual media item.
 * @property genres List of genres associated with the visual media item.
 * @property demographics List of demographics or target audiences for the visual media item.
 */

sealed class FullVisualMedia{

    abstract val id : Int
    abstract val type : String?
    abstract val images : List<ImageUrl>
    abstract val titles : List<Title>
    abstract val score : Float
    abstract val numberOfScorers: Long
    abstract val rank: Int
    abstract val synopsis: String
    abstract val genres: List<Int>
    abstract val demographics: List<Int>

}