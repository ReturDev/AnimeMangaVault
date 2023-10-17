package github.returdev.animemangavault.domain.model.reduced


/**
 * Data class representing reduced information about manga.
 *
 * @property id Unique identifier of the manga.
 * @property imageUrl URL of the image associated with the manga.
 * @property defaultTitle Title of the manga.
 * @property score Score or rating of the manga.
 */
data class ReducedManga(
    override val id: Int,
    override val imageUrl: String,
    override val defaultTitle: String,
    override val score: Float
) : ReducedVisualMedia()