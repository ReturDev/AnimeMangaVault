package github.returdev.animemangavault.ui.model.components.manga

import androidx.annotation.StringRes
import github.returdev.animemangavault.R

/**
 * Enum class representing different statuses of manga.
 *
 * @property stringResource The string resource ID for the corresponding status name.
 */
enum class MangaStatus(@StringRes val stringResource : Int) {

    PUBLISHING(R.string.manga_status_publishing),
    FINISHED (R.string.status_finished),
    ON_HIATUS(R.string.manga_status_hiatus),
    DISCONTINUED(R.string.manga_status_discontinued),
    UPCOMING(R.string.status_upcoming);

    companion object{
        /**
         * Retrieves a [MangaStatus] value based on a given string.
         *
         * @param value The string representing the status.
         * @return The corresponding [MangaStatus] value.
         */
        fun getStatus(value : String) : MangaStatus {
            val formattedValue = value.replace(" ","").lowercase()
            return MangaStatus.values().first{ status ->
                status.name.replace("_","").lowercase() == formattedValue
            }
        }
    }

}