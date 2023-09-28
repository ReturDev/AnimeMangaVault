package github.returdev.animemangavault.domain.search

import github.returdev.animemangavault.core.model.core.filters.SearchFilters
import github.returdev.animemangavault.data.cache.repository.AnimeCacheRepository
import javax.inject.Inject

class SearchAnimeUseCase @Inject constructor(
    private val animeCacheRepository: AnimeCacheRepository
) {

    operator fun invoke(title : String, filters: SearchFilters.AnimeFilters){
        animeCacheRepository.getAnimeSearch(title, filters)
    }


}