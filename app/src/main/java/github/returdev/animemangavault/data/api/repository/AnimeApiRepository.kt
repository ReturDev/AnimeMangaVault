package github.returdev.animemangavault.data.api.repository


import github.returdev.animemangavault.core.model.core.filters.SearchFilters
import github.returdev.animemangavault.core.model.core.filters.common.SortDirection
import github.returdev.animemangavault.core.network.NetworkState
import github.returdev.animemangavault.data.api.core.caller.AnimeMangaApiCaller
import github.returdev.animemangavault.data.api.model.anime.AnimeApiResponse
import github.returdev.animemangavault.data.api.model.anime.AnimeSearchApiResponse
import github.returdev.animemangavault.data.api.service.ApiService
import kotlinx.coroutines.flow.StateFlow
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
     * @param networkState A [StateFlow] representing the current network state.
     * @return An [AnimeApiResponse] containing information about the anime.
     */
    suspend fun getAnimeById(id: String, networkState : StateFlow<NetworkState>): AnimeApiResponse {

        return caller.executeCall(
            networkState,
            apiService.getAnimeById(id)
        )

    }

    /**
     * Retrieves a list of anime based on search criteria.
     *
     * @param page The page number of results to retrieve.
     * @param limit The maximum number of results per page.
     * @param title The title to search for (optional).
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
     * @param networkState A [StateFlow] representing the current network state.
     * @return The response containing the list of anime based on the search criteria.
     */
    private suspend fun getAnimeSearch(
        page: Int,
        limit: Int = ApiService.MAX_REQUEST_LIMIT,
        title: String? = null,
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
        networkState : StateFlow<NetworkState>
    ): AnimeSearchApiResponse {

        val limitChecked = if(limit > ApiService.MAX_REQUEST_LIMIT){
            ApiService.MAX_REQUEST_LIMIT
        } else {
            limit
        }

        return caller.executeCall(
            networkState,
            apiService.getAnimeSearch(
                page, limitChecked, title, type, score, status, rating, genres, orderBy, sort,
                producers, startDate, endDate
            )
        )
    }

    /**
     * Get a list of anime based on search criteria from the API.
     *
     * @param page The page number of the search results.
     * @param title The title to filter anime by title or starting letter.
     * @param filters Additional filters to apply to the search.
     * @param networkState A [StateFlow] representing the current network state.
     * @return An [AnimeSearchApiResponse] containing a list of anime matching the search criteria.
     */
    suspend fun getAnimeSortSearch(
        page: Int,
        title: String,
        filters: SearchFilters.AnimeFilters,
        networkState : StateFlow<NetworkState>
    ): AnimeSearchApiResponse {


        return getAnimeSearch(
            page = page,
            limit = ApiService.MAX_REQUEST_LIMIT,
            title = title,
            type = filters.type?.toString(),
            status = filters.status?.toString(),
            genres = filters.genres.joinToString(","),
            orderBy = filters.orderBy?.toString(),
            sort = when(filters.sort){
                SortDirection.ASCENDANT -> "asc"
                SortDirection.DESCENDANT -> "desc"
            },
            networkState = networkState
        )
    }

    /**
     * Retrieves a list of top anime.
     *
     * @param page The page number of results to retrieve.
     * @param limit The maximum number of results per page.
     * @param type The type of anime (optional).
     * @param filter A filter parameter (optional).
     * @param networkState A [StateFlow] representing the current network state.
     * @return The response containing the list of top anime.
     */
    suspend fun getTopAnime(
        page: Int,
        limit: Int = ApiService.MAX_REQUEST_LIMIT,
        type: String? = null,
        filter: String? = null,
        networkState : StateFlow<NetworkState>
    ): AnimeSearchApiResponse {

        val limitChecked = if(limit > ApiService.MAX_REQUEST_LIMIT){
            ApiService.MAX_REQUEST_LIMIT
        } else {
            limit
        }

        return caller.executeCall(
            networkState,
            apiService.getTopAnime(page,limitChecked, type, filter)
        )

    }

    /**
     * Retrieves a list of currently airing anime.
     *
     * @param page The page number of results to retrieve.
     * @param limit The maximum number of results per page.
     * @param filter A filter parameter (optional).
     * @param networkState A [StateFlow] representing the current network state.
     * @return The response containing the list of currently airing anime.
     */
    suspend fun getAnimeSeasonNow(
        page: Int,
        limit: Int = ApiService.MAX_REQUEST_LIMIT,
        filter: String? = null,
        networkState : StateFlow<NetworkState>
    ): AnimeSearchApiResponse {

        val limitChecked = if(limit > ApiService.MAX_REQUEST_LIMIT){
            ApiService.MAX_REQUEST_LIMIT
        } else {
            limit
        }

        return caller.executeCall(
            networkState,
            apiService.getAnimeSeasonNow(page, limitChecked, filter)
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
     * @param networkState A [StateFlow] representing the current network state.
     * @return The response containing the list of anime for the specified season and year.
     */
    suspend fun getAnimeSeason(
        year: String,
        season: String,
        page: Int,
        limit: Int = ApiService.MAX_REQUEST_LIMIT,
        filter: String? = null,
        networkState : StateFlow<NetworkState>
    ): AnimeSearchApiResponse {

        val limitChecked = if(limit > ApiService.MAX_REQUEST_LIMIT){
            ApiService.MAX_REQUEST_LIMIT
        } else {
            limit
        }

        return caller.executeCall(
            networkState,
            apiService.getAnimeSeason(year, season, page, limitChecked, filter)
        )

    }

}