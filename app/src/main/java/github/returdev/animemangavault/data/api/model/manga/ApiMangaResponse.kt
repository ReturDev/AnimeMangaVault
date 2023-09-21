package github.returdev.animemangavault.data.api.model.manga

import com.google.gson.annotations.SerializedName
import github.returdev.animemangavault.data.api.model.core.components.ApiDataGenre
import github.returdev.animemangavault.data.api.model.core.components.ApiDataImage
import github.returdev.animemangavault.data.api.model.core.components.ApiDataReleased
import github.returdev.animemangavault.data.api.model.core.components.ApiDataTitle

/**
 * Represents a response containing manga data from the API.
 *
 * @property data The manga data contained in the API response.
 */
data class ApiMangaResponse(
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
     * @property genres The list of genres associated with the manga.
     * @property demographics The list of demographics associated with the manga.
     * @property chapters The number of chapters in the manga.
     * @property volumes The number of volumes of the manga.
     * @property publishing Indicates whether the manga is currently publishing.
     * @property published The date range of when the manga was published.
     */
    data class ApiMangaExtendedDataResponse(
        @SerializedName("mal_id") val id : Int,
        @SerializedName("images") val images : ApiDataImage,
        @SerializedName("titles") val titles : List<ApiDataTitle>,
        @SerializedName("type") val type : String?,
        @SerializedName("score") val score : Float,
        @SerializedName("scored_by") val numberOfScorers : Long,
        @SerializedName("rank") val rank : Int,
        @SerializedName("synopsis") val synopsis : String,
        @SerializedName("genres") val genres : List<ApiDataGenre>,
        @SerializedName("demographics") val demographics : List<ApiDataGenre>,
        @SerializedName("chapters") val chapters : Int,
        @SerializedName("volumes") val volumes : Int,
        @SerializedName("publishing") val publishing : Boolean,
        @SerializedName("published") val published : ApiDataReleased
    )
}