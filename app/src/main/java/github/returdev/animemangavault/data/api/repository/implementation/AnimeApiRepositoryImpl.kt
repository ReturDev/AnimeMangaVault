package github.returdev.animemangavault.data.api.repository.implementation


import github.returdev.animemangavault.core.extensions.toFullAnime
import github.returdev.animemangavault.core.extensions.toReducedAnime
import github.returdev.animemangavault.core.model.core.filters.SearchFilters
import github.returdev.animemangavault.core.model.core.filters.anime.AnimeTypeFilters
import github.returdev.animemangavault.core.model.core.filters.common.SortDirection
import github.returdev.animemangavault.core.network.NetworkState
import github.returdev.animemangavault.data.api.core.caller.AnimeMangaApiCaller
import github.returdev.animemangavault.data.api.model.anime.AnimeApiResponse
import github.returdev.animemangavault.data.api.model.anime.AnimeSearchApiResponse
import github.returdev.animemangavault.data.api.repository.AnimeApiRepository
import github.returdev.animemangavault.data.api.service.ApiService
import github.returdev.animemangavault.domain.model.full.FullAnime
import github.returdev.animemangavault.domain.model.reduced.ReducedAnime
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
class AnimeApiRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val caller : AnimeMangaApiCaller
) : AnimeApiRepository {

    /**
     * Retrieves anime information by its unique identifier.
     *
     * @param id The unique identifier of the anime.
     * @param networkState A [StateFlow] representing the current network state.
     * @return An [FullAnime] containing the anime information.
     */
     override suspend fun getAnimeById(id: String, networkState : StateFlow<NetworkState>): FullAnime {

        return caller.executeCall(
            networkState,
            apiService.getAnimeById(id)
        ).data.toFullAnime()

    }

    /**
     * Get a list of anime based on search criteria from the API.
     *
     * @param page The page number of the search results.
     * @param title The title to filter anime by title or starting letter.
     * @param networkState A [StateFlow] representing the current network state.
     * @return An [AnimeSearchApiResponse] containing a list of anime matching the search criteria.
     */
    override suspend fun getAnimeSearch(
        page: Int,
        title: String,
        filters: SearchFilters.AnimeFilters,
        networkState: StateFlow<NetworkState>
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
     * @param networkState A [StateFlow] representing the current network state.
     * @return An [AnimeSearchApiResponse] containing the list of top anime.
     */
    override suspend fun getTopAnimesResponse(
        page: Int,
        limit: Int,
        type: AnimeTypeFilters?,
        networkState: StateFlow<NetworkState>
    ): AnimeSearchApiResponse {

        val limitChecked = if(limit > ApiService.MAX_REQUEST_LIMIT){
            ApiService.MAX_REQUEST_LIMIT
        } else {
            limit
        }

        return caller.executeCall(
            networkState,
            apiService.getTopAnime(page,limitChecked, type?.toString())
        )

    }

    /**
     * Retrieves a list of top anime.
     *
     * @param page The page number of results to retrieve.
     * @param limit The maximum number of results per page.
     * @param type The type of anime (optional).
     * @param networkState A [StateFlow] representing the current network state.
     * @return A [ReducedAnime] list of top anime.
     */
    override suspend fun getTopAnimes(
        page: Int,
        limit: Int,
        type: AnimeTypeFilters?,
        networkState: StateFlow<NetworkState>
    ): List<ReducedAnime> {

        val limitChecked = if(limit > ApiService.MAX_REQUEST_LIMIT){
            ApiService.MAX_REQUEST_LIMIT
        } else {
            limit
        }

        return caller.executeCall(
            networkState,
            apiService.getTopAnime(page,limitChecked, type?.toString())
        ).data.map { animes -> animes.toReducedAnime() }

    }

    /**
     * Retrieves a list of currently airing anime.
     *
     * @param page The page number of results to retrieve.
     * @param limit The maximum number of results per page.
     * @param type The type of anime (optional).
     * @param networkState A [StateFlow] representing the current network state.
     * @return An [ReducedAnime] list of currently airing anime.
     */
    override suspend fun getAnimeCurrentSeason(
        page: Int,
        limit: Int,
        type: AnimeTypeFilters?,
        networkState: StateFlow<NetworkState>
    ): List<ReducedAnime> {

        val limitChecked = if(limit > ApiService.MAX_REQUEST_LIMIT){
            ApiService.MAX_REQUEST_LIMIT
        } else {
            limit
        }

        return caller.executeCall(
            networkState,
            apiService.getAnimeSeasonNow(page, limitChecked, type?.toString())
        ).data.map { anime -> anime.toReducedAnime() }

    }

    override suspend fun getAnimeSeason(
        year: String,
        season: String,
        page: Int,
        limit: Int,
        type: AnimeTypeFilters?,
        networkState: StateFlow<NetworkState>
    ): AnimeSearchApiResponse {

        val limitChecked = if(limit > ApiService.MAX_REQUEST_LIMIT){
            ApiService.MAX_REQUEST_LIMIT
        } else {
            limit
        }

        return caller.executeCall(
            networkState,
            apiService.getAnimeSeason(year, season, page, limitChecked, type?.toString())
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


}