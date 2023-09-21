package github.returdev.animemangavault.data.api.model.anime

import com.google.gson.annotations.SerializedName
import github.returdev.animemangavault.data.api.model.core.components.DataGenreResponseComponent
import github.returdev.animemangavault.data.api.model.core.components.DataImageResponseComponent
import github.returdev.animemangavault.data.api.model.core.pagination.PaginationResponseComponent

/**
 * Represents the response for a list of anime from the API.
 *
 * @property pagination The pagination information for the list of anime.
 * @property data List of reduced anime data from the API response.
 */
data class AnimeSearchApiResponse (
    @SerializedName("pagination") val pagination: PaginationResponseComponent,
    @SerializedName("data") val data : List<ApiAnimeReducedDataResponse>
){

    /**
     * Represents reduced information about an anime from the API response.
     *
     * @property id The unique identifier of the anime.
     * @property images An object containing image URLs associated with the anime.
     * @property title The title of the anime.
     * @property type The type of the anime.
     * @property genres List of genres associated with the anime.
     * @property demographics List of demographics associated with the anime.
     * @property score The score of the anime.
     */
    data class ApiAnimeReducedDataResponse(
        @SerializedName("mal_id") val id: Int,
        @SerializedName("images") val images: DataImageResponseComponent,
        @SerializedName("title") val title: String,
        @SerializedName("type") val type: String?,
        @SerializedName("genres") val genres: List<DataGenreResponseComponent>,
        @SerializedName("demographics") val demographics: List<DataGenreResponseComponent>,
        @SerializedName("score") val score: Float
    )
}