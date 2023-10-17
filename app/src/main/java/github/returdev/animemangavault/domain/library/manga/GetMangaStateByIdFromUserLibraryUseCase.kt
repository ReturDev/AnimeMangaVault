package github.returdev.animemangavault.domain.library.manga

import github.returdev.animemangavault.core.model.library.UserLibraryVisualMediaStates
import github.returdev.animemangavault.data.library.repository.MangaLibraryRepository
import javax.inject.Inject

class GetMangaStateByIdFromUserLibraryUseCase @Inject constructor(
    private val mangaLibraryRepository: MangaLibraryRepository
) {

    suspend operator fun invoke(mangaId : Int) : UserLibraryVisualMediaStates? {
        return mangaLibraryRepository.getMangaStateById(mangaId)
    }

}