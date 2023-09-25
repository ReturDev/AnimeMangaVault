package github.returdev.animemangavault.domain.library.anime

import github.returdev.animemangavault.core.model.library.UserLibraryVisualMediaStates
import github.returdev.animemangavault.data.library.repository.AnimeLibraryRepository
import javax.inject.Inject

class GetAnimeStateByIdFromUserLibraryUseCase @Inject constructor(
    private val animeLibraryRepository: AnimeLibraryRepository
) {

    operator fun invoke(animeId : Int): UserLibraryVisualMediaStates? {
       return animeLibraryRepository.getAnimeStateById(animeId)
    }

}