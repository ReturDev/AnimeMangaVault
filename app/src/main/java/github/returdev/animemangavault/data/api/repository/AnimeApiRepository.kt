package github.returdev.animemangavault.data.api.repository


import github.returdev.animemangavault.data.api.model.anime.AnimeApiResponse
import github.returdev.animemangavault.data.api.model.anime.AnimeSearchApiResponse
import github.returdev.animemangavault.data.api.model.core.caller.AnimeMangaApiCaller
import github.returdev.animemangavault.data.api.service.ApiService
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Repository class for interacting with the Anime API.
 * It provides methods to retrieve information about anime and manga.
 *
 * @property apiService The [ApiService] used to make API requests.
 * @property caller The [AnimeMangaApiCaller] responsible for executing API calls with retries.
 */
@Singleton
class AnimeApiRepository @Inject constructor(
    private val apiService: ApiService,
    private val caller : AnimeMangaApiCaller
) {

    /**
     * Retrieves anime information by its unique identifier.
     *
     * @param id The unique identifier of the anime.
     * @param hasNetworkConnection Indicates whether there is an active network connection.
     * @return The response containing anime information.
     */
    suspend fun getAnimeById(id: String, hasNetworkConnection : Boolean): AnimeApiResponse {

        return caller.executeCall(
            hasNetworkConnection,
            apiService.getAnimeById(id)
        )

    }

    /**
     * Retrieves a list of anime based on search criteria.
     *
     * @param page The page number of results to retrieve.
     * @param limit The maximum number of results per page.
     * @param title The title to search for (optional).
     * @param startWithLetter Filter anime titles starting with a specific letter (optional).
     * @param type The type of anime (optional).
     * @param score The minimum score of anime (optional).
     * @param status The status of anime (optional).
     * @param rating The rating of anime (optional).
     * @param genres The genres of anime (optional).
     * @param orderBy The field to order the results by (optional).
     * @param sort The sorting order (optional).
     * @param producers The producers of anime (optional).
     * @param startDate The start date filter (optional).
     * @param endDate The end date filter (optional).
     * @param hasNetworkConnection Indicates whether there is an active network connection.
     * @return The response containing the list of anime based on the search criteria.
     */
    suspend fun getAnimeSearch(
        page: Int,
        limit: Int,
        title: String? = null,
        startWithLetter: String? = null,
        type: String? = null,
        score: Int? = null,
        status: String? = null,
        rating: String? = null,
        genres: String? = null,
        orderBy: String? = null,
        sort: String? = null,
        producers: String? = null,
        startDate: String? = null,
        endDate: String? = null,
        hasNetworkConnection : Boolean
    ): AnimeSearchApiResponse {

        return caller.executeCall(
            hasNetworkConnection,
            apiService.getAnimeSearch(
                page, limit, title, startWithLetter,
                type, score, status, rating, genres, orderBy, sort,
                producers, startDate, endDate
            )
        )
    }

    /**
     * Retrieves a list of top anime.
     *
     * @param page The page number of results to retrieve.
     * @param limit The maximum number of results per page.
     * @param type The type of anime (optional).
     * @param filter A filter parameter (optional).
     * @param hasNetworkConnection Indicates whether there is an active network connection.
     * @return The response containing the list of top anime.
     */
    suspend fun getTopAnime(
        page: Int,
        limit: Int,
        type: String? = null,
        filter: String? = null,
        hasNetworkConnection : Boolean
    ): AnimeSearchApiResponse {

        return caller.executeCall(
            hasNetworkConnection,
            apiService.getTopAnime(page,limit, type, filter)
        )

    }

    /**
     * Retrieves a list of currently airing anime.
     *
     * @param page The page number of results to retrieve.
     * @param limit The maximum number of results per page.
     * @param filter A filter parameter (optional).
     * @param hasNetworkConnection Indicates whether there is an active network connection.
     * @return The response containing the list of currently airing anime.
     */
    suspend fun getAnimeSeasonNow(
        page: Int,
        limit: Int,
        filter: String? = null,
        hasNetworkConnection: Boolean
    ): AnimeSearchApiResponse {

        return caller.executeCall(
            hasNetworkConnection,
            apiService.getAnimeSeasonNow(page, limit, filter)
        )

    }

    /**
     * Retrieves a list of anime for a specific season and year.
     *
     * @param year The year of the season.
     * @param season The season (e.g., "spring", "summer").
     * @param page The page number of results to retrieve.
     * @param limit The maximum number of results per page.
     * @param filter A filter parameter (optional).
     * @param hasNetworkConnection Indicates whether there is an active network connection.
     * @return The response containing the list of anime for the specified season and year.
     */
    suspend fun getAnimeSeason(
        year: String,
        season: String,
        page: Int,
        limit: Int,
        filter: String? = null,
        hasNetworkConnection: Boolean
    ): AnimeSearchApiResponse {

        return caller.executeCall(
            hasNetworkConnection,
            apiService.getAnimeSeason(year, season, page, limit, filter)
        )

    }

}