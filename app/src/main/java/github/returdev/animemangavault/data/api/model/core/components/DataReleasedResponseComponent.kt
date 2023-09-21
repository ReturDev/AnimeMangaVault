package github.returdev.animemangavault.data.api.model.core.components

import com.google.gson.annotations.SerializedName

/**
 * Represents the released information of an API data item.
 *
 *  @property date The date property containing the start and end dates.
 */
data class DataReleasedResponseComponent(
    @SerializedName("prop") val date : DateProp
) {

    /**
     * Represents the start and end dates of the released information.
     *
     * @property startDate The start date of the release.
     * @property endDate The end date of the release.
     */
    data class DateProp(
        @SerializedName("from") val startDate: DateResponse,
        @SerializedName("to") val endDate: DateResponse?
    ){
        /**
         * Represents a date response with day, month, and year.
         * @property day The day of the date.
         * @property month The month of the date.
         * @property year The year of the date.
         */
        data class DateResponse(
            @SerializedName("day") val day: Int,
            @SerializedName("month") val month: Int,
            @SerializedName("year")val year: Int
        )
    }

}