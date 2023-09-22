package github.returdev.animemangavault.data.api.repository


import github.returdev.animemangavault.data.api.model.core.caller.AnimeMangaApiCaller
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
class MangaApiRepository @Inject constructor(
    private val apiService: ApiService,
    private val caller : AnimeMangaApiCaller
) {

    /**
     * Retrieves manga information by its unique identifier.
     *
     * @param id The unique identifier of the manga.
     * @param hasNetworkConnection Indicates whether there is an active network connection.
     * @return The response containing manga information.
     */
    suspend fun getMangaById(id: String, hasNetworkConnection : StateFlow<Boolean>): MangaApiResponse {

        return caller.executeCall(
            hasNetworkConnection,
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
     * @param hasNetworkConnection Indicates whether there is an active network connection.
     * @return The response containing the list of manga based on the search criteria.
     */
    private suspend fun getMangaSearch(
        page: Int,
        limit: Int,
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
        hasNetworkConnection : StateFlow<Boolean>
    ): MangaSearchApiResponse {

        return caller.executeCall(
            hasNetworkConnection,
            apiService.getMangaSearch(
                page, limit, title, type, score, status, genres,
                orderBy, sort, magazines, startDate, endDate
            )
        )

    }

    /**
     * Retrieves a list of top manga.
     *
     * @param page The page number of results to retrieve.
     * @param limit The maximum number of results per page.
     * @param type The type of manga (optional).
     * @param filter A filter parameter (optional).
     * @param hasNetworkConnection Indicates whether there is an active network connection.
     * @return The response containing the list of top manga.
     */
    suspend fun getTopManga(
        page: Int,
        limit: Int,
        type: String? = null,
        filter : String? = null,
        hasNetworkConnection : StateFlow<Boolean>
    ): MangaSearchApiResponse {

        return caller.executeCall(
            hasNetworkConnection,
            apiService.getTopManga(page, limit, type, filter)
        )

    }

}