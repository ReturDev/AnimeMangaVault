package github.returdev.animemangavault.data.api.repository

import github.returdev.animemangavault.core.model.core.filters.SearchFilters
import github.returdev.animemangavault.core.model.core.filters.manga.MangaTypeFilters
import github.returdev.animemangavault.core.network.NetworkState
import github.returdev.animemangavault.data.api.model.manga.MangaSearchApiResponse
import github.returdev.animemangavault.data.api.service.ApiService
import github.returdev.animemangavault.domain.model.full.FullAnime
import github.returdev.animemangavault.domain.model.full.FullManga
import github.returdev.animemangavault.domain.model.reduced.ReducedManga
import kotlinx.coroutines.flow.StateFlow

interface MangaApiRepository {

    /**
     * Retrieves manga information by its unique identifier.
     *
     * @param id The unique identifier of the manga.
     * @param networkState A [StateFlow] representing the current network state.
     * @return An [FullAnime] containing manga information.
     */
    suspend fun getMangaById(id: String, networkState : StateFlow<NetworkState>): FullManga

    /**
     * Get sorted manga data based on the search filters from the API.
     *
     * @param page The page number of the search results.
     * @param title The text to search for in the manga title.
     * @param filters The filters to apply to the search (e.g., type, status, genres, orderBy, sort).
     * @param networkState A [StateFlow] representing the current network state.
     * @return An [MangaSearchApiResponse] containing a list of sorted manga based on the search parameters.
     */
    suspend fun getMangaSearch(
        page : Int,
        title : String,
        filters : SearchFilters.MangaFilters,
        networkState : StateFlow<NetworkState>
    ): MangaSearchApiResponse

    /**
     * Retrieves a list of top manga.
     *
     * @param page The page number of results to retrieve.
     * @param limit The maximum number of results per page.
     * @param type The type of manga (optional).
     * @param networkState A [StateFlow] representing the current network state.
     * @return An [ReducedManga] list of top manga.
     */
    suspend fun getTopManga(
        page: Int,
        limit: Int = ApiService.MAX_REQUEST_LIMIT,
        type: MangaTypeFilters? = null,
        networkState : StateFlow<NetworkState>
    ): List<ReducedManga>

}