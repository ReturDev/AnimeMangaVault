package github.returdev.animemangavault.domain.library.anime

import github.returdev.animemangavault.core.model.library.UserLibraryVisualMediaStates
import github.returdev.animemangavault.data.library.repository.AnimeLibraryRepository
import github.returdev.animemangavault.domain.model.reduced.ReducedAnime
import javax.inject.Inject

class SaveAnimeToUserLibraryUseCase @Inject constructor(
    private val animeLibraryRepository: AnimeLibraryRepository
) {

    suspend operator fun invoke(state : UserLibraryVisualMediaStates, reducedAnime: ReducedAnime) {

        animeLibraryRepository.insertAnime(state, reducedAnime)

    }

}