package github.returdev.animemangavault.data.library.repository

import androidx.paging.PagingData
import github.returdev.animemangavault.core.model.library.UserLibraryOrderBy
import github.returdev.animemangavault.core.model.library.UserLibrarySortDirection
import github.returdev.animemangavault.core.model.library.UserLibraryVisualMediaStates
import github.returdev.animemangavault.domain.model.reduced.ReducedManga
import kotlinx.coroutines.flow.Flow

interface MangaLibraryRepository {

    fun insertManga(state: UserLibraryVisualMediaStates, reducedManga: ReducedManga)

    fun getMangasByState(
        state: UserLibraryVisualMediaStates,
        orderBy: UserLibraryOrderBy,
        sortDirection: UserLibrarySortDirection
    ): Flow<PagingData<ReducedManga>>

    fun getMangaStateById(id : Int) : UserLibraryVisualMediaStates?

    fun updateMangaState(id : Int, newState : UserLibraryVisualMediaStates) : Int

    fun deleteManga(id : Int) : Int

}