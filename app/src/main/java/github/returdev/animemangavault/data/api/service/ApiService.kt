package github.returdev.animemangavault.data.api.service


import github.returdev.animemangavault.data.api.model.anime.AnimeApiResponse
import github.returdev.animemangavault.data.api.model.anime.AnimeSearchApiResponse
import github.returdev.animemangavault.data.api.model.manga.MangaApiResponse
import github.returdev.animemangavault.data.api.model.manga.MangaSearchApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    companion object{
        const val MAX_REQUEST_LIMIT = 25
    }

    /**
     * Get anime data by ID.
     *
     * @param id The ID of the anime.
     * @return A Call object with the API response containing anime data.
     */
    @GET("anime/{id}")
    fun getAnimeById(@Path("id") id : String) : Call<AnimeApiResponse>


    /**
     * Search for anime data.
     *
     * @param page The page number.
     * @param limit The limit of results per page. The maximum Api limit is 25.
     * @param title The title of the anime.
     * @param type The type of the anime.
     * @param score The score of the anime.
     * @param status The status of the anime.
     * @param rating The rating of the anime.
     * @param genres The genres ids of the anime. For more than one, separate the different values by comma. (e.g. 1,5,8)
     * @param orderBy The field to order the results by.
     * @param sort The sort order (ascending or descending).
     * @param producers The producers ids of the anime. For more than one, separate the different values by comma. (e.g. 4,5,12)
     * @param startDate The start date of the anime (Format: YYYY-MM-DD).
     * @param endDate The end date of the anime (Format: YYYY-MM-DD).
     * @return A Call object with the API response containing anime search results.
     */
    @GET("anime")
    fun getAnimeSearch(
        @Query("page") page : Int,
        @Query("limit") limit : Int,
        @Query("q") title : String?,
        @Query("type") type : String?,
        @Query("score") score : Int?,
        @Query("status") status : String?,
        @Query("rating") rating : String?,
        @Query("genres") genres : String?,
        @Query("order_by") orderBy : String?,
        @Query("sort") sort : String?,
        @Query("producers") producers : String?,
        @Query("start_date") startDate : String?,
        @Query("end_date") endDate : String?
    ) : Call<AnimeSearchApiResponse>


    /**
     * Get manga data by ID.
     * @param id The ID of the manga.
     * @return A Call object with the API response containing manga data.
     */
    @GET("manga/{id}")
    fun getMangaById(@Path("id") id : String) : Call<MangaApiResponse>


    /**
     * Search for manga data.
     *
     * @param page The page number.
     * @param limit The limit of results per page. The maximum Api limit is 25.
     * @param title The title of the manga.
     * @param type The type of the manga.
     * @param score The score of the manga.
     * @param status The status of the manga.
     * @param genres The genres ids of the manga. For more than one, separate the different values by comma. (e.g. 1,5,8)
     * @param orderBy The field to order the results by.
     * @param sort The sort order (ascending or descending).
     * @param magazines The magazines ids of the manga. For more than one, separate the different values by comma. (e.g. 4,5,12)
     * @param startDate The start date of the manga (Format: YYYY-MM-DD).
     * @param endDate The end date of the manga (Format: YYYY-MM-DD).
     * @return A Call object with the API response containing manga search results.
     */
    @GET("manga")
    fun getMangaSearch(
        @Query("page") page : Int,
        @Query("limit") limit : Int,
        @Query("q") title : String?,
        @Query("type") type : String?,
        @Query("score") score : Int?,
        @Query("status") status : String?,
        @Query("genres") genres : String?,
        @Query("order_by") orderBy : String?,
        @Query("sort") sort : String?,
        @Query("magazines") magazines : String?,
        @Query("start_date") startDate : String?,
        @Query("end_date") endDate : String?
    ) : Call<MangaSearchApiResponse>

    /**
     * Get the top anime list.
     * @param page The page number.
     * @param limit The limit of results per page. The maximum Api limit is 25.
     * @param type The type of anime.
     * @param filter The filter for the anime list.
     * @return A Call object with the API response containing the top anime list.
     */
    @GET("top/anime")
    fun getTopAnime(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("type") type: String?,
        @Query("filter") filter: String?
    ): Call<AnimeSearchApiResponse>


    /**
     * Get the top manga list.
     * @param page The page number.
     * @param limit The limit of results per page. The maximum Api limit is 25.
     * @param type The type of manga.
     * @param filter The filter for the manga list.
     * @return A Call object with the API response containing the top manga list.
     */
    @GET("top/manga")
    fun getTopManga(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("type") type : String?,
        @Query("filter") filter : String?
    ) : Call<MangaSearchApiResponse>


    /**
     * Get the currently airing anime for the current season.
     * @param page The page number.
     * @param limit The limit of results per page. The maximum Api limit is 25.
     * @param filter The filter for the anime list.
     * @return A Call object with the API response containing the currently airing anime for the current season.
     */
    @GET("seasons/now")
    fun getAnimeSeasonNow(
        @Query("page") page : Int,
        @Query("limit") limit: Int,
        @Query("filter") filter : String?,
    ) : Call<AnimeSearchApiResponse>

    /**
     * Get the anime for a specific season.
     * @param year The year of the season.
     * @param season The season (e.g., "spring", "summer", "fall", "winter").
     * @param page The page number.
     * @param limit The limit of results per page. The maximum Api limit is 25.
     * @param filter The filter for the anime list.
     * @return A Call object with the API response containing the anime for the specified season.
     */
    @GET("seasons/{year}/{season}")
    fun getAnimeSeason(
        @Path("year") year : String,
        @Path("season") season : String,
        @Query("page") page : Int,
        @Query("limit") limit: Int,
        @Query("filter") filter : String?,
    ) : Call<AnimeSearchApiResponse>

}