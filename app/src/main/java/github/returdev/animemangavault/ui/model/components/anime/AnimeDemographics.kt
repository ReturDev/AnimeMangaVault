package github.returdev.animemangavault.ui.model.components.anime

import androidx.annotation.StringRes
import github.returdev.animemangavault.R

/**
 * Enum class representing different explicit manga genres.
 *
 * @property id The unique identifier of the explicit genre.
 * @property stringResource The string resource ID for the explicit genre name.
 */
enum class AnimeDemographics (val id : Int, @StringRes val stringResource : Int){
    JOSEI(43, R.string.demographic_josei),
    KIDS(15, R.string.demographic_kids),
    SEINEN(42, R.string.demographic_seinen),
    SHOUJO(25, R.string.demographic_shoujo),
    SHOUNEN(27, R.string.demographic_shounen);

    companion object{
        /**
         * Get the ExplicitGenres enum value based on the provided id.
         *
         * @param id The unique identifier of the explicit genre.
         * @return The corresponding ExplicitGenres enum value.
         */
        fun valueOf(id : Int) = AnimeDemographics.values().first{ d -> d.id == id}

    }

}