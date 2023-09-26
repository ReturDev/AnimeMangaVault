package github.returdev.animemangavault.ui.model.filters.core

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import github.returdev.animemangavault.R

/**
 * Enum class representing the sorting direction options for library items.
 *
 * @param abbreviationResource The resource ID of the abbreviation for the sorting direction.
 * @param iconResource The resource ID of the icon representing the sorting direction.
 */
enum class SortDirectionUi(
    @StringRes val abbreviationResource : Int,
    @DrawableRes val iconResource: Int
) {

    ASCENDANT(R.string.sort_direction_asc_abbreviation, R.drawable.ic_sort_asc),
    DESCENDANT(R.string.sort_direction_desc_abbreviation, R.drawable.ic_sort_desc)

}