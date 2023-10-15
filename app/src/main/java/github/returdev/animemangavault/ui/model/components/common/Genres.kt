package github.returdev.animemangavault.ui.model.components.common

import android.util.Log
import androidx.annotation.StringRes
import github.returdev.animemangavault.R

/**
 * Enum class representing different manga genres.
 *
 * @property id The unique identifier of the genre.
 * @property stringResource The string resource ID for the genre name.
 */
enum class Genres(val id : Int, @StringRes val stringResource : Int) {
    ACTION(1, R.string.genre_action),
    ADVENTURE(2, R.string.genre_adventure),
    AVANT_GARDE(5, R.string.genre_avant_garde),
    AWARD_WINNING(46, R.string.genre_award_winning),
    BOYS_LOVE(28, R.string.genre_boys_love),
    COMEDY(4, R.string.genre_comedy),
    DRAMA(8, R.string.genre_drama),
    ECCHI(9, R.string.explicit_genre_ecchi),
    EROTICA(49, R.string.explicit_genre_erotica),
    FANTASY(10, R.string.genre_fantasy),
    GIRLS_LOVE(26, R.string.genre_girls_love),
    GOURMET(47, R.string.genre_gourmet),
    HENTAI(12, R.string.explicit_genre_hentai),
    HORROR(14, R.string.genre_horror),
    MYSTERY(7, R.string.genre_mystery),
    ROMANCE(22, R.string.genre_romance),
    SCI_FI(24, R.string.genre_sci_fi),
    SLICE_OF_LIFE(36, R.string.genre_slice_of_life),
    SPORTS(30, R.string.genre_sports),
    SUPERNATURAL(37, R.string.genre_super_natural),
    ANIME_SUSPENSE(41, R.string.genre_suspense),
    MANGA_SUSPENSE(45, R.string.genre_suspense);


    companion object{
        /**
         * Get the Genres enum value based on the provided id.
         *
         * @param id The unique identifier of the genre.
         * @return The corresponding Genres enum value.
         */
        fun valueOf(id: Int): Genres = values().first { g -> g.id == id }
    }

}