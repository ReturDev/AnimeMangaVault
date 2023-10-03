package github.returdev.animemangavault.data.api.repository.implementation


import github.returdev.animemangavault.core.extensions.toManga
import github.returdev.animemangavault.core.extensions.toReducedManga
import github.returdev.animemangavault.core.model.core.filters.SearchFilters
import github.returdev.animemangavault.core.model.core.filters.common.SortDirection
import github.returdev.animemangavault.core.model.core.filters.manga.MangaTypeFilters
import github.returdev.animemangavault.core.network.NetworkState
import github.returdev.animemangavault.data.api.core.caller.AnimeMangaApiCaller
import github.returdev.animemangavault.data.api.model.manga.MangaSearchApiResponse
import github.returdev.animemangavault.data.api.repository.MangaApiRepository
import github.returdev.animemangavault.data.api.service.ApiService
import github.returdev.animemangavault.domain.model.full.FullAnime
import github.returdev.animemangavault.domain.model.full.FullManga
import github.returdev.animemangavault.domain.model.reduced.ReducedManga
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Repository class for interacting with the Manga API.
 * It provides methods to retrieve information about manga.
 *
 * @property apiService The [ApiService] used to make API requests.
 * @property caller The [AnimeMangaApiCaller] responsible for executing API calls with retries.
 */
@Singleton
class MangaApiRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val caller : AnimeMangaApiCaller
) : MangaApiRepository {

    /**
     * Retrieves manga information by its unique identifier.
     *
     * @param id The unique identifier of the manga.
     * @param networkState A [StateFlow] representing the current network state.
     * @return An [FullAnime] containing manga information.
     */
    override suspend fun getMangaById(
        id: String,
        networkState: StateFlow<NetworkState>
    ): FullManga {

        return caller.executeCall(
            networkState,
            apiService.getMangaById(id)
        ).data.toManga()

    }

    /**
     * Get sorted manga data based on the search filters from the API.
     *
     * @param page The page number of the search results.
     * @param title The text to search for in the manga title.
     * @param filters The filters to apply to the search (e.g., type, status, genres, orderBy, sort).
     * @param networkState A [StateFlow] representing the current network state.
     * @return An [MangaSearchApiResponse] containing a list of sorted manga based on the search parameters.
     */
    override suspend fun getMangaSearch(
        page: Int,
        title: String,
        filters: SearchFilters.MangaFilters,
        networkState: StateFlow<NetworkState>
    ): MangaSearchApiResponse {

        return getMangaSearch(
            page = page,
            limit = ApiService.MAX_REQUEST_LIMIT,
            title = title,
            type = filters.type?.toString(),
            status = filters.status?.toString(),
            genres = filters.genres.joinToString(","),
            orderBy = filters.orderBy?.toString(),
            sort = when (filters.sort) {
                SortDirection.ASCENDANT -> "asc"
                SortDirection.DESCENDANT -> "desc"
            },
            networkState = networkState
        )

    }

    /**
     * Retrieves a list of top manga.
     *
     * @param page The page number of results to retrieve.
     * @param limit The maximum number of results per page.
     * @param type The type of manga (optional).
     * @param networkState A [StateFlow] representing the current network state.
     * @return The response containing the list of top manga.
     */
    override suspend fun getTopManga(
        page: Int,
        limit: Int,
        type: MangaTypeFilters?,
        networkState: StateFlow<NetworkState>
    ): List<ReducedManga> {

        val limitChecked = if (limit > ApiService.MAX_REQUEST_LIMIT) {
            ApiService.MAX_REQUEST_LIMIT
        } else {
            limit
        }

        return caller.executeCall(
            networkState,
            apiService.getTopManga(page, limitChecked, type?.toString())
        ).data.map { manga -> manga.toReducedManga() }

    }

    /**
     * Retrieves a list of manga based on search criteria.
     *
     * @param page The page number of results to retrieve.
     * @param limit The maximum number of results per page.
     * @param title The title to search for (optional).
     * @param type The type of manga (optional).
     * @param score The minimum score of manga (optional).
     * @param status The status of manga (optional).
     * @param genres The genres of manga (optional).
     * @param orderBy The field to order the results by (optional).
     * @param sort The sorting order (optional).
     * @param magazines The magazines of manga (optional).
     * @param startDate The start date filter (optional).
     * @param endDate The end date filter (optional).
     * @param networkState A [StateFlow] representing the current network state.
     * @return The response containing the list of manga based on the search criteria.
     */
    private suspend fun getMangaSearch(
        page: Int,
        limit: Int = ApiService.MAX_REQUEST_LIMIT,
        title: String? = null,
        type: String? = null,
        score: Int? = null,
        status: String? = null,
        genres: String? = null,
        orderBy: String? = null,
        sort: String? = null,
        magazines: String? = null,
        startDate: String? = null,
        endDate: String? = null,
        networkState: StateFlow<NetworkState>
    ): MangaSearchApiResponse {

        val limitChecked = if (limit > ApiService.MAX_REQUEST_LIMIT) {
            ApiService.MAX_REQUEST_LIMIT
        } else {
            limit
        }

        return caller.executeCall(
            networkState,
            apiService.getMangaSearch(
                page, limitChecked, title, type, score, status, genres,
                orderBy, sort, magazines, startDate, endDate
            )
        )

    }

}
