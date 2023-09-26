package github.returdev.animemangavault.data.api.model.components

import com.google.gson.annotations.SerializedName

/**
 * Represents a genre associated with an API data item.
 * @property id The ID of the genre.
 */
data class DataGenreResponseComponent(
    @SerializedName("mal_id") val id : Int
)
