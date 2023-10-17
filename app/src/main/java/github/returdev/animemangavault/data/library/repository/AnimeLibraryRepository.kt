package github.returdev.animemangavault.data.library.repository

import androidx.paging.PagingData
import github.returdev.animemangavault.core.model.core.filters.common.SortDirection
import github.returdev.animemangavault.core.model.library.UserLibraryOrderBy
import github.returdev.animemangavault.core.model.library.UserLibraryVisualMediaStates
import github.returdev.animemangavault.domain.model.reduced.ReducedAnime
import kotlinx.coroutines.flow.Flow

interface AnimeLibraryRepository {

    suspend fun insertAnime(state : UserLibraryVisualMediaStates, reducedAnime: ReducedAnime)

    fun getAnimesByState(
        state : UserLibraryVisualMediaStates,
        orderBy : UserLibraryOrderBy,
        sortDirection : SortDirection
    ) : Flow<PagingData<ReducedAnime>>

    suspend fun getAnimeStateById(id : Int) : UserLibraryVisualMediaStates?

    suspend fun updateAnimeState(id : Int, newState : UserLibraryVisualMediaStates) : Int

    suspend fun deleteAnime(id : Int) : Int

}