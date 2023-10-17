package github.returdev.animemangavault.domain.detail

import github.returdev.animemangavault.core.network.NetworkConnectivity
import github.returdev.animemangavault.data.api.repository.AnimeApiRepository
import github.returdev.animemangavault.domain.model.full.FullAnime
import javax.inject.Inject

class GetAnimeByIdUseCase @Inject constructor(
    private val animeApiRepository: AnimeApiRepository,
    private val networkConnectivity: NetworkConnectivity
) {

    suspend operator fun invoke(id : String): FullAnime {

       return animeApiRepository.getAnimeById(id, networkConnectivity.networkState)

    }

}