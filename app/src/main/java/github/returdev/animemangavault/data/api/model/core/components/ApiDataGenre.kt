package github.returdev.animemangavault.data.api.model.core.components

import com.google.gson.annotations.SerializedName

/**
 * Represents a genre associated with an API data item.
 * @property id The ID of the genre.
 */
data class ApiDataGenre(
    @SerializedName("mal_id") val id : Int
)
