package github.returdev.animemangavault.data.cache.repository

import androidx.paging.PagingData
import github.returdev.animemangavault.core.model.core.filters.Filters
import github.returdev.animemangavault.domain.model.basic.BasicManga
import kotlinx.coroutines.flow.Flow

interface MangaCacheRepository {

    fun getMangaSearch(title : String, filters: Filters): Flow<PagingData<BasicManga>>

    fun clearCache()

    fun clearAndResetPrimaryKey()

}