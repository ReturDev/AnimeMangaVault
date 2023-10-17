package github.returdev.animemangavault.domain.search

import androidx.paging.PagingData
import github.returdev.animemangavault.core.model.core.filters.SearchFilters
import github.returdev.animemangavault.data.cache.repository.MangaCacheRepository
import github.returdev.animemangavault.domain.model.basic.BasicManga
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchMangaUseCase @Inject constructor(
    private val mangaCacheRepository: MangaCacheRepository
) {

    operator fun invoke(title : String, filters : SearchFilters.MangaFilters): Flow<PagingData<BasicManga>> {
        return mangaCacheRepository.getMangaSearch(title, filters)
    }

}