package github.returdev.animemangavault.ui.model.components.manga

import androidx.annotation.StringRes
import github.returdev.animemangavault.R

/**
 * Enum class representing different types of manga.
 *
 * @property stringResource The string resource ID for the type name.
 */
enum class MangaTypes(@StringRes val stringResource : Int){
    MANGA(R.string.manga_type_manga),
    NOVEL(R.string.manga_type_novel),
    LIGHTNOVEL(R.string.manga_type_light_novel),
    ONESHOT(R.string.manga_type_one_shot),
    DOUJIN(R.string.manga_type_doujin),
    MANHWA(R.string.manga_type_manhwa),
    MANHUA(R.string.manga_type_manhua),
    UNKNOWN(R.string.type_unknown);

    companion object{
        fun getMangaType(value : String?) : MangaTypes {

            return value?.let {
                val formattedValue = it.replace(" ","").lowercase()
                MangaTypes.values().firstOrNull() {type -> type.name.lowercase() == formattedValue }
            } ?: UNKNOWN
        }
    }
}
