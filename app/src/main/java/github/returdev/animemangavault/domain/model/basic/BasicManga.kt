package github.returdev.animemangavault.domain.model.basic

import github.returdev.animemangavault.domain.model.components.ImageUrl

/**
 * A data class representing basic information about a manga.
 * Extends [BasicVisualMedia] class.
 *
 * @property id The unique identifier of the manga.
 * @property images List of image URLs associated with the manga.
 * @property title The title of the manga.
 * @property score The score of the manga.
 * @property type The type of the manga (TV, Movie, OVA, etc.).
 * @property genres List of genres associated with the manga.
 * @property demographics List of demographics associated with the manga.
 */
data class BasicManga(
    override val id: Int,
    override val images: List<ImageUrl>,
    override val title: String,
    override val score: Float,
    override val type: String,
    override val genres: List<String>,
    override val demographics: List<String>
) : BasicVisualMedia()