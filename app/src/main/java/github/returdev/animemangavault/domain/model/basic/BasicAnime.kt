package github.returdev.animemangavault.domain.model.basic

import github.returdev.animemangavault.core.model.components.ImageUrl

/**
 * A data class representing basic information about an anime.
 * Extends [BasicVisualMedia] class.
 *
 * @property id The unique identifier of the anime.
 * @property images List of image URLs associated with the anime.
 * @property title The title of the anime.
 * @property score The score of the anime.
 * @property type The type of the anime (TV, Movie, OVA, etc.).
 * @property genres List of genres associated with the anime.
 * @property demographics List of demographics associated with the anime.
 */
data class BasicAnime(
    override val id: Int,
    override val images: List<ImageUrl>,
    override val title: String,
    override val score: Float,
    override val type: String?,
    override val genres: List<Int>,
    override val demographics: List<Int>

) : BasicVisualMedia()