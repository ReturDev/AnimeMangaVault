package github.returdev.animemangavault.domain.library.manga

import github.returdev.animemangavault.core.model.library.UserLibraryVisualMediaStates
import github.returdev.animemangavault.data.library.repository.MangaLibraryRepository
import github.returdev.animemangavault.domain.model.reduced.ReducedManga
import javax.inject.Inject

class SaveMangaToUserLibraryUseCase @Inject constructor(
    private val mangaLibraryRepository: MangaLibraryRepository
) {

    suspend operator fun invoke(state : UserLibraryVisualMediaStates, reducedManga: ReducedManga){
        mangaLibraryRepository.insertManga(state, reducedManga)
    }

}