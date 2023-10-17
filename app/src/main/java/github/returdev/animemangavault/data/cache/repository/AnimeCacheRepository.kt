package github.returdev.animemangavault.data.cache.repository

import androidx.paging.PagingData
import github.returdev.animemangavault.core.model.core.filters.SearchFilters
import github.returdev.animemangavault.domain.model.basic.BasicAnime
import kotlinx.coroutines.flow.Flow

interface AnimeCacheRepository {

    fun getAnimeSearch(title : String, filters: SearchFilters.AnimeFilters): Flow<PagingData<BasicAnime>>

    fun getThisSeason() : Flow<PagingData<BasicAnime>>

    fun clearAndResetPrimaryKey()

}