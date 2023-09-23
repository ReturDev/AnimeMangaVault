package github.returdev.animemangavault.domain.model.library


/**
 * Data class representing a manga visual media item in a library.
 *
 * @property id Unique identifier of the manga.
 * @property imageUrl URL of the image associated with the manga.
 * @property defaultTitle Title of the manga.
 * @property score Score or rating of the manga.
 */
data class LibraryManga(
    override val id: Int,
    override val imageUrl: String,
    override val defaultTitle: String,
    override val score: Float
) : LibraryVisualMedia()