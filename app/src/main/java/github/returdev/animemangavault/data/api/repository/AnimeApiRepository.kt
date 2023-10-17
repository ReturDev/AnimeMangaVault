package github.returdev.animemangavault.data.api.repository

import github.returdev.animemangavault.core.model.core.filters.SearchFilters
import github.returdev.animemangavault.core.model.core.filters.anime.AnimeTypeFilters
import github.returdev.animemangavault.core.network.NetworkState
import github.returdev.animemangavault.data.api.model.anime.AnimeSearchApiResponse
import github.returdev.animemangavault.data.api.service.ApiService
import github.returdev.animemangavault.domain.model.full.FullAnime
import github.returdev.animemangavault.domain.model.reduced.ReducedAnime
import kotlinx.coroutines.flow.StateFlow

interface AnimeApiRepository {

    /**
     * Retrieves anime information by its unique identifier.
     *
     * @param id The unique identifier of the anime.
     * @param networkState A [StateFlow] representing the current network state.
     * @return An [FullAnime] containing the anime information.
     */
    suspend fun getAnimeById(id: String, networkState : StateFlow<NetworkState>): FullAnime

    /**
     * Get a list of anime based on search criteria from the API.
     *
     * @param page The page number of the search results.
     * @param title The title to filter anime by title or starting letter.
     * @param networkState A [StateFlow] representing the current network state.
     * @return An [AnimeSearchApiResponse] containing a list of anime matching the search criteria.
     */
    suspend fun getAnimeSearch(
        page: Int,
        title: String,
        filters: SearchFilters.AnimeFilters,
        networkState : StateFlow<NetworkState>
    ): AnimeSearchApiResponse

    /**
     * Retrieves a list of top anime.
     *
     * @param page The page number of results to retrieve.
     * @param limit The maximum number of results per page.
     * @param type The type of anime (optional).
     * @param networkState A [StateFlow] representing the current network state.
     * @return An [AnimeSearchApiResponse] containing the list of top anime.
     */
    suspend fun getTopAnimesResponse(
        page: Int,
        limit: Int = ApiService.MAX_REQUEST_LIMIT,
        type: AnimeTypeFilters? = null,
        networkState : StateFlow<NetworkState>
    ): AnimeSearchApiResponse

    /**
     * Retrieves a list of top anime.
     *
     * @param page The page number of results to retrieve.
     * @param limit The maximum number of results per page.
     * @param type The type of anime (optional).
     * @param networkState A [StateFlow] representing the current network state.
     * @return A [ReducedAnime] list of top anime.
     */
    suspend fun getTopAnimes(
        page: Int,
        limit: Int = ApiService.MAX_REQUEST_LIMIT,
        type: AnimeTypeFilters? = null,
        networkState : StateFlow<NetworkState>
    ): List<ReducedAnime>

    /**
     * Retrieves a list of currently airing anime.
     *
     * @param page The page number of results to retrieve.
     * @param limit The maximum number of results per page.
     * @param type The type of anime (optional).
     * @param networkState A [StateFlow] representing the current network state.
     * @return An [AnimeSearchApiResponse] containing the list of currently airing anime.
     */
    suspend fun getAnimeCurrentSeasonResponse(
        page: Int,
        limit: Int = ApiService.MAX_REQUEST_LIMIT,
        type: AnimeTypeFilters? = null,
        networkState : StateFlow<NetworkState>
    ): AnimeSearchApiResponse

    /**
     * Retrieves a list of currently airing anime.
     *
     * @param page The page number of results to retrieve.
     * @param limit The maximum number of results per page.
     * @param type The type of anime (optional).
     * @param networkState A [StateFlow] representing the current network state.
     * @return An [ReducedAnime] list of currently airing anime.
     */
    suspend fun getAnimeCurrentSeasonList(
        page: Int,
        limit: Int = ApiService.MAX_REQUEST_LIMIT,
        type: AnimeTypeFilters? = null,
        networkState : StateFlow<NetworkState>
    ): List<ReducedAnime>

    /**
     * Retrieves a list of anime for a specific season and year.
     *
     * @param year The year of the season.
     * @param season The season (e.g., "spring", "summer").
     * @param page The page number of results to retrieve.
     * @param limit The maximum number of results per page.
     * @param type The type of anime (optional).
     * @param networkState A [StateFlow] representing the current network state.
     * @return An [ReducedAnime] list of anime for the specified season and year.
     */
    suspend fun getAnimeSeason(
        year: String,
        season: String,
        page: Int,
        limit: Int = ApiService.MAX_REQUEST_LIMIT,
        type: AnimeTypeFilters? = null,
        networkState : StateFlow<NetworkState>
    ): AnimeSearchApiResponse

}