package github.returdev.animemangavault.data.library.repository

import androidx.paging.PagingData
import github.returdev.animemangavault.data.library.core.UserLibraryOrderBy
import github.returdev.animemangavault.data.library.core.UserLibrarySortDirection
import github.returdev.animemangavault.data.library.core.UserLibraryStates
import github.returdev.animemangavault.data.library.model.entity.AnimeLibraryEntity
import github.returdev.animemangavault.domain.model.reduced.ReducedAnime
import kotlinx.coroutines.flow.Flow

interface AnimeLibraryRepository {

    fun insertAnime(animeLibraryEntity : AnimeLibraryEntity)

    fun getAnimesByState(
        state : UserLibraryStates,
        orderBy : UserLibraryOrderBy,
        sortDirection : UserLibrarySortDirection
    ) : Flow<PagingData<ReducedAnime>>

    fun getAnimeStateById(id : Int) : UserLibraryStates?

    fun updateAnimeState(id : Int, newState : UserLibraryStates) : Int

}