package github.returdev.animemangavault.data.api.repository.implementation


import github.returdev.animemangavault.core.model.core.filters.SearchFilters
import github.returdev.animemangavault.core.model.core.filters.common.SortDirection
import github.returdev.animemangavault.core.network.NetworkState
import github.returdev.animemangavault.data.api.core.caller.AnimeMangaApiCaller
import github.returdev.animemangavault.data.api.model.manga.MangaApiResponse
import github.returdev.animemangavault.data.api.model.manga.MangaSearchApiResponse
import github.returdev.animemangavault.data.api.service.ApiService
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
) {

    /**
     * Retrieves manga information by its unique identifier.
     *
     * @param id The unique identifier of the manga.
     * @param networkState A [StateFlow] representing the current network state.
     * @return The response containing manga information.
     */
    suspend fun getMangaById(id: String, networkState : StateFlow<NetworkState>): MangaApiResponse {

        return caller.executeCall(
            networkState,
            apiService.getMangaById(id)
        )

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
        networkState : StateFlow<NetworkState>
    ): MangaSearchApiResponse {

        val limitChecked = if(limit > ApiService.MAX_REQUEST_LIMIT){
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

    /**
     * Get sorted manga data based on the search filters from the API.
     *
     * @param page The page number of the search results.
     * @param title The text to search for in the manga title.
     * @param filters The filters to apply to the search (e.g., type, status, genres, orderBy, sort).
     * @param networkState A [StateFlow] representing the current network state.
     * @return An [MangaSearchApiResponse] containing a list of sorted manga based on the search parameters.
     */
    suspend fun getMangaSortSearch(
        page : Int,
        title : String,
        filters : SearchFilters.MangaFilters,
        networkState : StateFlow<NetworkState>
    ): MangaSearchApiResponse {

        return getMangaSearch(
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
     * Retrieves a list of top manga.
     *
     * @param page The page number of results to retrieve.
     * @param limit The maximum number of results per page.
     * @param type The type of manga (optional).
     * @param filter A filter parameter (optional).
     * @param networkState A [StateFlow] representing the current network state.
     * @return The response containing the list of top manga.
     */
    suspend fun getTopManga(
        page: Int,
        limit: Int = ApiService.MAX_REQUEST_LIMIT,
        type: String? = null,
        filter : String? = null,
        networkState : StateFlow<NetworkState>
    ): MangaSearchApiResponse {

        val limitChecked = if(limit > ApiService.MAX_REQUEST_LIMIT){
            ApiService.MAX_REQUEST_LIMIT
        } else {
            limit
        }

        return caller.executeCall(
            networkState,
            apiService.getTopManga(page, limitChecked, type, filter)
        )

    }

}