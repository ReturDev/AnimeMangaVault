package github.returdev.animemangavault.domain.library.anime

import github.returdev.animemangavault.data.library.repository.AnimeLibraryRepository
import javax.inject.Inject

class DeleteAnimeFromUserLibraryUseCase @Inject constructor(
    private val animeLibraryRepository: AnimeLibraryRepository
) {

    operator fun invoke(animeId : Int) : Int{
        return animeLibraryRepository.deleteAnime(animeId)
    }

}