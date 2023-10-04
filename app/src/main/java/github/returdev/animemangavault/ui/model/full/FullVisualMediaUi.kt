package github.returdev.animemangavault.ui.model.full


import github.returdev.animemangavault.core.model.components.ImageUrl
import github.returdev.animemangavault.core.model.components.Title
import github.returdev.animemangavault.ui.model.components.common.Genres

/**
 * Sealed class representing detailed information about visual media for UI purposes, such as anime and manga.
 *
 * @property id Unique identifier for the visual media.
 * @property type The type of the visual media.
 * @property images List of image URLs associated with the visual media.
 * @property titles List of titles associated with the visual media.
 * @property score The score or rating of the visual media.
 * @property numberOfScorers The number of users who have scored or rated the visual media.
 * @property rank The rank of the visual media.
 * @property synopsis A synopsis or summary of the visual media.
 * @property genres List of genres associated with the visual media.
 * @property demographics List of demographics or target audiences for the visual media.
 */
sealed class FullVisualMediaUi{

    abstract val id : Int
    abstract val type : Any
    abstract val images : List<ImageUrl>
    abstract val titles : List<Title>
    abstract val score : Float
    abstract val numberOfScorers: Long
    abstract val rank: Int
    abstract val synopsis: String
    abstract val status : Any
    abstract val genres: List<Genres>
    abstract val demographics: List<Any>

}