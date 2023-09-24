package github.returdev.animemangavault.ui.model.components.common

import androidx.annotation.StringRes
import github.returdev.animemangavault.R


enum class Demographics(val id : IntArray, @StringRes val stringResource : Int){
    JOSEI(intArrayOf(43), R.string.demographic_josei),
    KIDS(intArrayOf(15), R.string.demographic_kids),
    SEINEN(intArrayOf(41,42), R.string.demographic_seinen),
    SHOUJO(intArrayOf(25), R.string.demographic_shoujo),
    SHOUNEN(intArrayOf(27), R.string.demographic_shounen);

    companion object{

        fun valueOf(id : Int) = Demographics.values().first{ d -> d.id.contains(id)}
    }

}