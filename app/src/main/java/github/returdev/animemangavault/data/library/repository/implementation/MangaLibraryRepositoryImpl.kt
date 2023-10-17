package github.returdev.animemangavault.data.library.repository.implementation

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import github.returdev.animemangavault.core.extensions.toMangaLibraryEntity
import github.returdev.animemangavault.core.extensions.toReducedMangaUi
import github.returdev.animemangavault.core.model.core.filters.common.SortDirection
import github.returdev.animemangavault.core.model.library.UserLibraryOrderBy
import github.returdev.animemangavault.core.model.library.UserLibraryVisualMediaStates
import github.returdev.animemangavault.data.library.dao.MangaLibraryDao
import github.returdev.animemangavault.data.library.repository.MangaLibraryRepository
import github.returdev.animemangavault.data.library.repository.core.LibraryRepositoryUtil
import github.returdev.animemangavault.domain.model.reduced.ReducedManga
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MangaLibraryRepositoryImpl @Inject constructor(
    private val mangaLibraryDao: MangaLibraryDao
) : MangaLibraryRepository {
    override suspend fun insertManga(state: UserLibraryVisualMediaStates, reducedManga: ReducedManga) {
        mangaLibraryDao.insertManga(
            reducedManga.toMangaLibraryEntity(state)
        )
    }


    override fun getMangasByState(
        state: UserLibraryVisualMediaStates,
        orderBy: UserLibraryOrderBy,
        sortDirection: SortDirection
    ): Flow<PagingData<ReducedManga>> {

        val orderByNum = when(orderBy){
            UserLibraryOrderBy.ADDED_DATE -> 1
            UserLibraryOrderBy.DEFAULT_TITLE -> 2
            UserLibraryOrderBy.SCORE -> 3
        }

        return Pager(
            config = LibraryRepositoryUtil.getPagerConfig(),
            pagingSourceFactory = {

                when(sortDirection){
                    SortDirection.ASCENDANT -> mangaLibraryDao.getMangasByState(state.name, orderByNum)
                    SortDirection.DESCENDANT -> mangaLibraryDao.getMangasByStateDesc(state.name, orderByNum)
                }
            }
        ).flow.map { pagingData -> pagingData.map { entity -> entity.toReducedMangaUi() } }

    }

    override suspend fun getMangaStateById(id: Int): UserLibraryVisualMediaStates? {
        return mangaLibraryDao.getMangaStateById(id)?.let { state ->
            UserLibraryVisualMediaStates.valueOf(state)
        }
    }

    override suspend fun updateMangaState(id: Int, newState: UserLibraryVisualMediaStates): Int {
        return mangaLibraryDao.updateMangaState(id, newState.name)
    }

    override suspend fun deleteManga(id: Int): Int {
        return mangaLibraryDao.deleteManga(id)
    }

}