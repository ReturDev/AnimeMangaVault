package github.returdev.animemangavault.domain.model.reduced



/**
 * Data class representing reduced information about anime.
 *
 * @property id Unique identifier of the anime.
 * @property imageUrl URL of the image associated with the anime.
 * @property defaultTitle Title of the anime.
 * @property score Score or rating of the anime.
 */
data class ReducedAnime (
    override val id: Int,
    override val imageUrl: String,
    override val defaultTitle: String,
    override val score: Float
) : ReducedVisualMedia()