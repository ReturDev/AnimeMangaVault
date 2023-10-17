package github.returdev.animemangavault.domain.library

import github.returdev.animemangavault.core.model.library.UserLibraryVisualMediaStates
import github.returdev.animemangavault.domain.library.anime.SaveAnimeToUserLibraryUseCase
import github.returdev.animemangavault.domain.library.manga.SaveMangaToUserLibraryUseCase
import github.returdev.animemangavault.domain.model.reduced.ReducedAnime
import github.returdev.animemangavault.domain.model.reduced.ReducedManga
import github.returdev.animemangavault.domain.model.reduced.ReducedVisualMedia
import javax.inject.Inject

class SaveVisualMediaToUserLibraryUseCase @Inject constructor(
    private val saveAnime : SaveAnimeToUserLibraryUseCase,
    private val saveManga : SaveMangaToUserLibraryUseCase
) {

    suspend operator fun invoke(newState : UserLibraryVisualMediaStates,reducedVM: ReducedVisualMedia){

        when(reducedVM){
            is ReducedAnime -> saveAnime(newState, reducedVM)
            is ReducedManga -> saveManga(newState, reducedVM)
        }

    }

}