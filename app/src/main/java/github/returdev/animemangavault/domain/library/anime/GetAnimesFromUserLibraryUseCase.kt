package github.returdev.animemangavault.domain.library.anime

import androidx.paging.PagingData
import github.returdev.animemangavault.core.model.library.UserLibraryOrderBy
import github.returdev.animemangavault.core.model.core.filters.SortDirection
import github.returdev.animemangavault.core.model.library.UserLibraryVisualMediaStates
import github.returdev.animemangavault.data.library.repository.AnimeLibraryRepository
import github.returdev.animemangavault.domain.model.reduced.ReducedAnime
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetAnimesFromUserLibraryUseCase @Inject constructor(
    private val animeLibraryRepository: AnimeLibraryRepository
) {

    operator fun invoke (
        state : UserLibraryVisualMediaStates,
        orderBy: UserLibraryOrderBy,
        sortDirection: SortDirection
    ): Flow<PagingData<ReducedAnime>> {
        return animeLibraryRepository.getAnimesByState(state, orderBy, sortDirection)
    }

}