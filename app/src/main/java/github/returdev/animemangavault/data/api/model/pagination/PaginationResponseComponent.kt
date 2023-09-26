package github.returdev.animemangavault.data.api.model.pagination

import com.google.gson.annotations.SerializedName

/**
 * Represents pagination information in an API response.
 *
 * @property lastVisiblePage The index of the last visible page.
 * @property hasNextPage Indicates whether there is a next page available.
 * @property itemsOnPageInfo Additional page information within the pagination.
 */
data class PaginationResponseComponent (
    @SerializedName("last_visible_page") val lastVisiblePage : Int,
    @SerializedName("has_next_page") val hasNextPage : Boolean,
    @SerializedName("items") val itemsOnPageInfo : PageInfo
){

    /**
     * Represents additional page information within the pagination.
     *
     * @property total The total count of items across all pages.
     */
    data class PageInfo(
        @SerializedName("total") val total : Int
    )

}