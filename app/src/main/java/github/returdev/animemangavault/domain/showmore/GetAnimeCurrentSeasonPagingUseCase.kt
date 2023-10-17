package github.returdev.animemangavault.domain.showmore

import androidx.paging.PagingData
import github.returdev.animemangavault.data.cache.repository.AnimeCacheRepository
import github.returdev.animemangavault.domain.model.basic.BasicAnime
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAnimeCurrentSeasonPagingUseCase @Inject constructor(
    private val animeCacheRepository: AnimeCacheRepository
) {

    operator fun invoke(): Flow<PagingData<BasicAnime>> {
        return animeCacheRepository.getThisSeason()
    }

}