package github.returdev.animemangavault.domain.home

import github.returdev.animemangavault.core.model.core.filters.anime.AnimeTypeFilters
import github.returdev.animemangavault.core.network.NetworkConnectivity
import github.returdev.animemangavault.data.api.repository.AnimeApiRepository
import github.returdev.animemangavault.domain.model.reduced.ReducedAnime
import javax.inject.Inject

class GetTopAnimesUseCase @Inject constructor(
    private val animeApiRepository: AnimeApiRepository,
    private val networkConnectivity: NetworkConnectivity
) {

    suspend operator fun invoke(
        page : Int,
        limit : Int,
        type : AnimeTypeFilters? = null
    ): List<ReducedAnime> {
        return animeApiRepository.getTopAnimes(
            page = page,
            limit = limit,
            type = type,
            networkState = networkConnectivity.networkState
        )
    }

}