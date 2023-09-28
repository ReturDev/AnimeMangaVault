package github.returdev.animemangavault.data.library.repository.core

import androidx.paging.PagingConfig

object LibraryRepositoryUtil {

    private const val PAGE_SIZE = 15
    private const val PREFETCH_DISTANCE = 8
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