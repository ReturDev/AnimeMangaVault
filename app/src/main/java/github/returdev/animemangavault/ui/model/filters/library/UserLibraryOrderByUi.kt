package github.returdev.animemangavault.ui.model.filters.library

import androidx.annotation.StringRes
import github.returdev.animemangavault.R

enum class UserLibraryOrderByUi (@StringRes val stringResource : Int) {

    ADDED_DATE(R.string.order_by_added_date),
    DEFAULT_TITLE(R.string.order_by_title),
    SCORE(R.string.order_by_score),

}