package github.returdev.animemangavault.domain.library.anime

import github.returdev.animemangavault.core.model.library.UserLibraryVisualMediaStates
import github.returdev.animemangavault.data.library.repository.AnimeLibraryRepository
import javax.inject.Inject

class UpdateAnimeStateInUserLibraryUseCase @Inject constructor(
    private val animeLibraryRepository: AnimeLibraryRepository
) {

    operator fun invoke(animeId : Int, newState : UserLibraryVisualMediaStates) : Int{
        return animeLibraryRepository.updateAnimeState(animeId, newState)
    }

}