package github.returdev.animemangavault.ui.model.components.anime

import androidx.annotation.StringRes
import github.returdev.animemangavault.R

/**
 * Enum class representing different types of anime.
 * @property stringResource The string resource ID for the type name.
 */
enum class AnimeTypes(@StringRes val stringResource : Int) {
    TV(R.string.anime_type_tv),
    MOVIE(R.string.anime_type_movie),
    OVA(R.string.anime_type_ova),
    SPECIAL(R.string.anime_type_special),
    ONA(R.string.anime_type_ona),
    MUSIC(R.string.anime_type_music),
    UNKNOWN(R.string.type_unknown);

    companion object{
        fun getAnimeType(value : String?) : AnimeTypes{
            return value?.let {
                val formattedValue = it.replace(" ","").lowercase()
                AnimeTypes.values().first { type -> type.name.lowercase() == formattedValue }
            } ?: UNKNOWN
        }
    }

}