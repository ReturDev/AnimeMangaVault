package github.returdev.animemangavault.ui.model.components.manga

import androidx.annotation.StringRes
import github.returdev.animemangavault.R

/**
 * Enum class representing different manga demographics.
 *
 * @property id The unique identifier of the demographic.
 * @property stringResource The string resource ID for the demographic name.
 */
enum class MangaDemographics(val id : Int, @StringRes val stringResource : Int){
    JOSEI(43, R.string.demographic_josei),
    KIDS(15, R.string.demographic_kids),
    SEINEN(41, R.string.demographic_seinen),
    SHOUJO(25, R.string.demographic_shoujo),
    SHOUNEN(27, R.string.demographic_shounen);


    companion object{
        /**
         * Get the MangaDemographics enum value based on the provided id.
         *
         * @param id The unique identifier of the demographic.
         * @return The corresponding MangaDemographics enum value.
         */
        fun valueOf(id : Int) = MangaDemographics.values().first{ d -> d.id == id}
    }
}