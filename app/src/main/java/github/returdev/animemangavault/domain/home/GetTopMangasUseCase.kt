package github.returdev.animemangavault.domain.home

import github.returdev.animemangavault.core.model.core.filters.manga.MangaTypeFilters
import github.returdev.animemangavault.core.network.NetworkConnectivity
import github.returdev.animemangavault.data.api.repository.MangaApiRepository
import github.returdev.animemangavault.domain.model.reduced.ReducedManga
import javax.inject.Inject

class GetTopMangasUseCase @Inject constructor(
    private val mangaApiRepository: MangaApiRepository,
    private val networkConnectivity: NetworkConnectivity
) {

    suspend operator fun invoke(
        page : Int,
        limit : Int,
        type : MangaTypeFilters? = null
    ): List<ReducedManga> {
        return mangaApiRepository.getTopManga(
            page = page,
            limit = limit,
            type = type,
            networkState = networkConnectivity.networkState
        )
    }

}