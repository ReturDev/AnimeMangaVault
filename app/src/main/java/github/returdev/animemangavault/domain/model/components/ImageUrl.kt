package github.returdev.animemangavault.domain.model.components

/**
 * Sealed class representing different image URLs.
 */
sealed class ImageUrl {
    /**
     * Abstract property for the URL string.
     */
    abstract val url: String

    /**
     * Data class representing the small image URL.
     *
     * @property url The URL string for the small image.
     */
    data class SmallImageUrl(override val url: String) : ImageUrl()

    /**
     * Data class representing the normal image URL.
     *
     * @property url The URL string for the normal image.
     */
    data class NormalImageUrl(override val url: String) : ImageUrl()

    /**
     * Data class representing the large image URL.
     *
     * @property url The URL string for the large image.
     */
    data class LargeImageUrl(override val url: String) : ImageUrl()
}