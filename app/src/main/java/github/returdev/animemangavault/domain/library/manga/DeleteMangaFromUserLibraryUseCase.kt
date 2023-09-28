package github.returdev.animemangavault.domain.library.manga

import github.returdev.animemangavault.data.library.repository.MangaLibraryRepository
import javax.inject.Inject

class DeleteMangaFromUserLibraryUseCase @Inject constructor(
    private val mangaLibraryRepository: MangaLibraryRepository
) {

    operator fun invoke(mangaId : Int) : Int{
        return mangaLibraryRepository.deleteManga(mangaId)
    }

}