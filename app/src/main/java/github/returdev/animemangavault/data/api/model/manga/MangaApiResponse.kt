package github.returdev.animemangavault.data.api.model.manga

import com.google.gson.annotations.SerializedName
import github.returdev.animemangavault.data.api.model.components.DataGenreResponseComponent
import github.returdev.animemangavault.data.api.model.components.DataImageResponseComponent
import github.returdev.animemangavault.data.api.model.components.DataReleasedResponseComponent
import github.returdev.animemangavault.data.api.model.components.DataTitleResponseComponent

/**
 * Represents a response containing manga data from the API.
 *
 * @property data The manga data contained in the API response.
 */
data class MangaApiResponse(
    @SerializedName("data") val data : ApiMangaExtendedDataResponse
){

    /**
     * Represents extended manga data from the API.
     *
     * @property id The ID of the manga.
     * @property images The images data of the manga.
     * @property titles The list of titles associated with the manga.
     * @property type The type of the manga.
     * @property score The score/rating of the manga.
     * @property numberOfScorers The number of users who scored the manga.
     * @property rank The rank of the manga.
     * @property synopsis The synopsis/description of the manga.
     * @property status The status of the manga (e.g., "Publishing," "Completed," etc.).
     * @property genres The list of genres associated with the manga.
     * @property demographics The list of demographics associated with the manga.
     * @property chapters The number of chapters in the manga.
     * @property volumes The number of volumes of the manga.
     * @property publishing Indicates whether the manga is currently publishing.
     * @property published The date range of when the manga was published.
     */
    data class ApiMangaExtendedDataResponse(
        @SerializedName("mal_id") val id : Int,
        @SerializedName("images") val images : DataImageResponseComponent,
        @SerializedName("titles") val titles : List<DataTitleResponseComponent>,
        @SerializedName("type") val type : String?,
        @SerializedName("score") val score : Float,
        @SerializedName("scored_by") val numberOfScorers : Long,
        @SerializedName("rank") val rank : Int,
        @SerializedName("synopsis") val synopsis : String,
        @SerializedName("status") val status : String,
        @SerializedName("genres") val genres : List<DataGenreResponseComponent>,
        @SerializedName("demographics") val demographics : List<DataGenreResponseComponent>,
        @SerializedName("chapters") val chapters : Int,
        @SerializedName("volumes") val volumes : Int,
        @SerializedName("publishing") val publishing : Boolean,
        @SerializedName("published") val published : DataReleasedResponseComponent
    )
}