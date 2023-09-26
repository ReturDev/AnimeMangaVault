package github.returdev.animemangavault.domain.library.manga

import androidx.paging.PagingData
import github.returdev.animemangavault.core.model.library.UserLibraryOrderBy
import github.returdev.animemangavault.core.model.library.UserLibrarySortDirection
import github.returdev.animemangavault.core.model.library.UserLibraryVisualMediaStates
import github.returdev.animemangavault.data.library.repository.MangaLibraryRepository
import github.returdev.animemangavault.domain.model.reduced.ReducedManga
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMangaFromUserLibraryUseCase @Inject constructor(
    private val mangaLibraryRepository: MangaLibraryRepository
) {

    operator fun invoke(
        state: UserLibraryVisualMediaStates,
        orderBy: UserLibraryOrderBy,
        sortDirection: UserLibrarySortDirection
    ): Flow<PagingData<ReducedManga>> {
        return mangaLibraryRepository.getMangasByState(state, orderBy, sortDirection)
    }

}