package github.returdev.animemangavault.domain.search

import github.returdev.animemangavault.data.cache.repository.AnimeCacheRepository
import github.returdev.animemangavault.data.cache.repository.MangaCacheRepository
import javax.inject.Inject

class ResetCacheUseCase @Inject constructor(
    private val animeCacheRepository: AnimeCacheRepository,
    private val mangaCacheRepository: MangaCacheRepository
) {

    operator fun invoke(){
        animeCacheRepository.clearAndResetPrimaryKey()
        mangaCacheRepository.clearAndResetPrimaryKey()
    }

}