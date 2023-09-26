package github.returdev.animemangavault.data.api.model.components

import com.google.gson.annotations.SerializedName

/**
 * Represents a title of an API data item.
 *
 * @property type The type of the title.
 * @property title The title value.
 */
data class DataTitleResponseComponent(
    @SerializedName("type") val type : String,
    @SerializedName("title") val title : String
)
