package github.returdev.animemangavault.ui.model.components.anime

import androidx.annotation.StringRes
import github.returdev.animemangavault.R

enum class Season(@StringRes val stringResource: Int) {

    SPRING(R.string.season_spring),
    SUMMER(R.string.season_summer),
    FALL(R.string.season_fall),
    WINTER(R.string.season_winter);

    companion object{
        fun getSeason(value : String): Season {
            return Season.values().first{season -> season.name.lowercase() == value }
        }
    }

}