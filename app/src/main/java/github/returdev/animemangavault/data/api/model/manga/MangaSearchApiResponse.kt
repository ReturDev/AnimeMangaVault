package github.returdev.animemangavault.data.api.model.manga

import com.google.gson.annotations.SerializedName
import github.returdev.animemangavault.data.api.model.components.DataGenreResponseComponent
import github.returdev.animemangavault.data.api.model.components.DataImageResponseComponent
import github.returdev.animemangavault.data.api.model.pagination.PaginationResponseComponent

/**
 * Represents a response containing a list of manga data from the API.
 *
 * @property pagination The pagination information for the list.
 * @property data The list of reduced manga data from the API.
 */
data class MangaSearchApiResponse(
    @SerializedName("pagination") val pagination: PaginationResponseComponent,
    @SerializedName("data") val data: List<ApiMangaReducedDataResponse>
) {

    /**
     * Represents reduced manga data from the API.
     *
     * @property id The ID of the manga.
     * @property images The images data of the manga.
     * @property title The title of the manga.
     * @property type The type of the manga.
     * @property genres The list of genres associated with the manga.
     * @property demographics The list of demographics associated with the manga.
     * @property score The score/rating of the manga.
     */
    data class ApiMangaReducedDataResponse(
        @SerializedName("mal_id") val id: Int,
        @SerializedName("images") val images: DataImageResponseComponent,
        @SerializedName("title") val title: String,
        @SerializedName("type") val type : String?,
        @SerializedName("genres") val genres: List<DataGenreResponseComponent>,
        @SerializedName("demographics") val demographics: List<DataGenreResponseComponent>,
        @SerializedName("score") val score: Float
    )
}
