package github.returdev.animemangavault.domain.search

import androidx.paging.PagingData
import github.returdev.animemangavault.core.model.core.filters.SearchFilters
import github.returdev.animemangavault.data.cache.repository.AnimeCacheRepository
import github.returdev.animemangavault.domain.model.basic.BasicAnime
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchAnimeUseCase @Inject constructor(
    private val animeCacheRepository: AnimeCacheRepository
) {

    operator fun invoke(title : String, filters: SearchFilters.AnimeFilters): Flow<PagingData<BasicAnime>> {
        return animeCacheRepository.getAnimeSearch(title, filters)
    }


}