package github.returdev.animemangavault.data.library.repository

import androidx.paging.PagingData
import github.returdev.animemangavault.core.model.library.UserLibraryOrderBy
import github.returdev.animemangavault.core.model.core.filters.common.SortDirection
import github.returdev.animemangavault.core.model.library.UserLibraryVisualMediaStates
import github.returdev.animemangavault.domain.model.reduced.ReducedManga
import kotlinx.coroutines.flow.Flow

interface MangaLibraryRepository {

    suspend fun insertManga(state: UserLibraryVisualMediaStates, reducedManga: ReducedManga)

    fun getMangasByState(
        state: UserLibraryVisualMediaStates,
        orderBy: UserLibraryOrderBy,
        sortDirection: SortDirection
    ): Flow<PagingData<ReducedManga>>

    suspend fun getMangaStateById(id : Int) : UserLibraryVisualMediaStates?

    suspend fun updateMangaState(id : Int, newState : UserLibraryVisualMediaStates) : Int

    suspend fun deleteManga(id : Int) : Int

}