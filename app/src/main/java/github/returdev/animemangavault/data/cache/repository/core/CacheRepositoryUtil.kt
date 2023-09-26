package github.returdev.animemangavault.data.cache.repository.core

import androidx.paging.PagingConfig
import github.returdev.animemangavault.data.api.service.ApiService

object CacheRepositoryUtil {

    /**
     * SQL query to reset the auto-incremented primary key in the sql parameter :table.
     */
    const val RESET_PRIMARY_KEY_AUTO_G = "DELETE FROM sqlite_sequence WHERE name = :table"

    private const val PAGE_SIZE = ApiService.MAX_REQUEST_LIMIT
    private const val PREFETCH_DISTANCE = PAGE_SIZE / 2
    private const val INITIAL_LOAD_SIZE = PAGE_SIZE * 2
    private const val MAX_SIZE = PAGE_SIZE + (PREFETCH_DISTANCE * 2)

    fun getPagerConfig(): PagingConfig {
        return PagingConfig(
            pageSize = PAGE_SIZE,
            prefetchDistance = PREFETCH_DISTANCE,
            enablePlaceholders = true,
            initialLoadSize = INITIAL_LOAD_SIZE ,
            maxSize = MAX_SIZE
        )
    }

}