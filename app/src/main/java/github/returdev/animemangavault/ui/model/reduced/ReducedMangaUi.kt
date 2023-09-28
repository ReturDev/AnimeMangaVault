package github.returdev.animemangavault.ui.model.reduced


/**
 * Data class representing reduced information about manga for UI purposes.
 *
 * @property id Unique identifier of the manga.
 * @property imageUrl URL of the image associated with the manga.
 * @property defaultTitle Title of the manga.
 * @property score Score or rating of the manga.
 */
data class ReducedMangaUi(
    override val id: Int,
    override val imageUrl: String,
    override val defaultTitle: String,
    override val score: Float
) : ReducedVisualMediaUi()