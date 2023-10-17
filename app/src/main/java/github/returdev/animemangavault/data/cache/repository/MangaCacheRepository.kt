package github.returdev.animemangavault.data.cache.repository

import androidx.paging.PagingData
import github.returdev.animemangavault.core.model.core.filters.SearchFilters
import github.returdev.animemangavault.domain.model.basic.BasicManga
import kotlinx.coroutines.flow.Flow

interface MangaCacheRepository {

    fun getMangaSearch(title : String, filters: SearchFilters.MangaFilters): Flow<PagingData<BasicManga>>


    fun clearAndResetPrimaryKey()

}