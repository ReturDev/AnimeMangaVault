package github.returdev.animemangavault.data.library.repository

import androidx.paging.PagingData
import github.returdev.animemangavault.data.library.core.UserLibraryOrderBy
import github.returdev.animemangavault.data.library.core.UserLibrarySortDirection
import github.returdev.animemangavault.data.library.core.UserLibraryStates
import github.returdev.animemangavault.data.library.model.entity.MangaLibraryEntity
import github.returdev.animemangavault.domain.model.reduced.ReducedManga
import kotlinx.coroutines.flow.Flow

interface MangaLibraryRepository {

    fun insertManga(mangaLibraryEntity: MangaLibraryEntity)

    fun getMangasByState(
        state: UserLibraryStates,
        orderBy: UserLibraryOrderBy,
        sortDirection: UserLibrarySortDirection
    ): Flow<PagingData<ReducedManga>>

    fun getMangaStateById(id : String) : UserLibraryStates?

    fun updateMangaState(id : String, newState : UserLibraryStates) : Int

}