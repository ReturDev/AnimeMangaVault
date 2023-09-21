package github.returdev.animemangavault.data.api.model.anime

import com.google.gson.annotations.SerializedName
import github.returdev.animemangavault.data.api.model.core.components.ApiDataGenre
import github.returdev.animemangavault.data.api.model.core.components.ApiDataImage
import github.returdev.animemangavault.data.api.model.core.components.ApiDataReleased
import github.returdev.animemangavault.data.api.model.core.components.ApiDataTitle

data class ApiAnimeResponse(
    @SerializedName("data") val data: ApiAnimeExtendedDataResponse
) {

    /**
     * Represents detailed information about an anime from the API response.
     *
     * @property id The unique identifier of the anime.
     * @property images An object containing image URLs associated with the anime.
     * @property titles List of titles (in different languages) associated with the anime.
     * @property type The type of the anime.
     * @property score The score of the anime.
     * @property numberOfScorers The number of people who scored the anime.
     * @property rank The rank of the anime.
     * @property synopsis The synopsis or summary of the anime.
     * @property genres List of genres associated with the anime.
     * @property demographics List of demographics associated with the anime.
     * @property source The source of the anime.
     * @property episodes The total number of episodes of the anime.
     * @property status The status of the anime (e.g., "Airing," "Completed," etc.).
     * @property airing Indicates if the anime is currently airing or not.
     * @property aired The release date information of the anime.
     * @property season The season in which the anime was released.
     */
    data class ApiAnimeExtendedDataResponse(
        @SerializedName("mal_id") val id: Int,
        @SerializedName("images") val images: ApiDataImage,
        @SerializedName("titles") val titles: List<ApiDataTitle>,
        @SerializedName("type") val type : String?,
        @SerializedName("score") val score: Float,
        @SerializedName("scored_by") val numberOfScorers: Long,
        @SerializedName("rank") val rank: Int,
        @SerializedName("synopsis") val synopsis: String,
        @SerializedName("genres") val genres: List<ApiDataGenre>,
        @SerializedName("demographics") val demographics: List<ApiDataGenre>,
        @SerializedName("source") val source: String,
        @SerializedName("episodes") val episodes: Int,
        @SerializedName("status") val status: String,
        @SerializedName("airing") val airing: Boolean,
        @SerializedName("aired") val aired: ApiDataReleased,
        @SerializedName("season") val season: String?
    )
}