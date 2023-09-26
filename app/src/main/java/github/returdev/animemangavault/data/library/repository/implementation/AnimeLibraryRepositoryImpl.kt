package github.returdev.animemangavault.data.library.repository.implementation

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import github.returdev.animemangavault.core.extensions.toAnimeLibraryEntity
import github.returdev.animemangavault.core.extensions.toReducedAnime
import github.returdev.animemangavault.core.model.library.UserLibraryOrderBy
import github.returdev.animemangavault.core.model.library.UserLibrarySortDirection
import github.returdev.animemangavault.core.model.library.UserLibraryVisualMediaStates
import github.returdev.animemangavault.data.library.dao.AnimeLibraryDao
import github.returdev.animemangavault.data.library.repository.AnimeLibraryRepository
import github.returdev.animemangavault.data.library.repository.core.LibraryRepositoryUtil
import github.returdev.animemangavault.domain.model.reduced.ReducedAnime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnimeLibraryRepositoryImpl @Inject constructor(
    private val animeLibraryDao: AnimeLibraryDao
) : AnimeLibraryRepository {

    override fun insertAnime(state: UserLibraryVisualMediaStates, reducedAnime: ReducedAnime) {
        animeLibraryDao.insertAnime(
            reducedAnime.toAnimeLibraryEntity(state)
        )
    }

    override fun getAnimesByState(
        state: UserLibraryVisualMediaStates,
        orderBy: UserLibraryOrderBy,
        sortDirection: UserLibrarySortDirection
    ): Flow<PagingData<ReducedAnime>> {

        val orderByNum = when(orderBy){
            UserLibraryOrderBy.ADDED_DATE -> 1
            UserLibraryOrderBy.DEFAULT_TITLE -> 2
            UserLibraryOrderBy.SCORE -> 3
        }

        return Pager(
            config = LibraryRepositoryUtil.getPagerConfig(),
            pagingSourceFactory = {
                when(sortDirection){
                    UserLibrarySortDirection.ASCENDANT -> animeLibraryDao.getAnimesByState(state.name, orderByNum)
                    UserLibrarySortDirection.DESCENDANT -> animeLibraryDao.getAnimeByStateDesc(state.name, orderByNum)
                }
            }
        ).flow.map { pagingData -> pagingData.map { entity -> entity.toReducedAnime()} }

    }

    override fun getAnimeStateById(id: Int): UserLibraryVisualMediaStates? {
        return animeLibraryDao.getAnimeStateById(id)?.let { status ->
            UserLibraryVisualMediaStates.valueOf(status)
        }
    }

    override fun updateAnimeState(id: Int, newState: UserLibraryVisualMediaStates) : Int {
        return animeLibraryDao.updateAnimeState(id, newState.name)
    }

    override fun deleteAnime(id: Int): Int {
        return animeLibraryDao.deleteAnime(id)
    }
}