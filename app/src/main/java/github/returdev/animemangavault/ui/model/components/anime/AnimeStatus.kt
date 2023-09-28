package github.returdev.animemangavault.ui.model.components.anime

import androidx.annotation.StringRes
import github.returdev.animemangavault.R

/**
 * Enum class representing different statuses of anime.
 *
 * @property stringResource The string resource ID for the corresponding status name.
 */
enum class AnimeStatus(@StringRes val stringResource : Int) {
    CURRENTLY_AIRING(R.string.anime_status_airing),
    FINISHED_AIRING(R.string.status_finished),
    NOT_YET_AIRED(R.string.status_upcoming);

    companion object{
        /**
         * Retrieves an [AnimeStatus] value based on a given string.
         *
         * @param value The string representing the status.
         * @return The corresponding [AnimeStatus] value, or the default if not found.
         */
        fun getStatus(value : String) : AnimeStatus{
            val formattedValue = value.replace(" ","").lowercase()
            return AnimeStatus.values().first{status ->
                status.name.replace("_","").lowercase() == formattedValue
            }
        }
    }

}