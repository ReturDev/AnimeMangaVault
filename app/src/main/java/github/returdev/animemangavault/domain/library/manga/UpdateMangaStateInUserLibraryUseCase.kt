package github.returdev.animemangavault.domain.library.manga

import github.returdev.animemangavault.core.model.library.UserLibraryVisualMediaStates
import github.returdev.animemangavault.data.library.repository.MangaLibraryRepository
import javax.inject.Inject

class UpdateMangaStateInUserLibraryUseCase @Inject constructor(
    private val mangaLibraryRepository: MangaLibraryRepository
) {

    operator fun invoke(mangaId : Int, newState : UserLibraryVisualMediaStates) : Int{
        return mangaLibraryRepository.updateMangaState(mangaId, newState)
    }

}