package github.returdev.animemangavault.domain.model.library

/**
 * Sealed class representing basic information about visual media such as anime and manga.
 *
 * @property id Unique identifier for the visual media.
 * @property imageUrl URL of the default image associated with the visual media.
 * @property defaultTitle The default title of the visual media.
 * @property score The score or rating of the visual media.
 */
sealed class LibraryVisualMedia {
    abstract val id: Int
    abstract val imageUrl : String
    abstract val defaultTitle : String
    abstract val score : Float
}

