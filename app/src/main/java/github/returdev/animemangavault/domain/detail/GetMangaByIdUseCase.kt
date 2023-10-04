package github.returdev.animemangavault.domain.detail

import github.returdev.animemangavault.core.network.NetworkConnectivity
import github.returdev.animemangavault.data.api.repository.MangaApiRepository
import github.returdev.animemangavault.domain.model.full.FullManga
import javax.inject.Inject

class GetMangaByIdUseCase @Inject constructor(
    private val mangaApiRepository: MangaApiRepository,
    private val networkConnectivity: NetworkConnectivity
) {

    suspend operator fun invoke(id : String): FullManga {

        return mangaApiRepository.getMangaById(id, networkConnectivity.networkState)

    }

}