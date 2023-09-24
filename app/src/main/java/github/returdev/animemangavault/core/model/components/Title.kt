package github.returdev.animemangavault.core.model.components

/**
 * Sealed class representing different types of titles associated with visual media items.
 * @property title Represents a title of a visual media item.
 */
sealed class Title{
    abstract val title : String
    data class DefaultTitle constructor(override val title: String) : Title()
    data class JapaneseTitle constructor(override val title: String) : Title()
    data class EnglishTitle constructor(override val title: String) : Title()
    data class SynonymTitle constructor(override val title: String) : Title()

    companion object {
        private const val DEFAULT_KEY = "Default"
        private const val JAPANESE_KEY = "Japanese"
        private const val ENGLISH_KEY = "English"
        private const val SYNONYM_KEY = "Synonym"

        /**
         * Creates a [Title] instance based on a provided key and title string.
         *
         * @param key The key representing the type of title (e.g., "Default", "Japanese").
         * @param title The title string.
         * @return A [Title] instance corresponding to the key, or null if the key is not recognized.
         */
        fun createTitleByKey(key: String, title: String): Title? {
            return when (key) {
                DEFAULT_KEY -> DefaultTitle(title)
                JAPANESE_KEY -> JapaneseTitle(title)
                ENGLISH_KEY -> EnglishTitle(title)
                SYNONYM_KEY -> SynonymTitle(title)
                else -> null
            }
        }
    }

}

