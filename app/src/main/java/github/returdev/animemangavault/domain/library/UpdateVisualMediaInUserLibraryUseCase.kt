package github.returdev.animemangavault.domain.library

import github.returdev.animemangavault.core.model.library.UserLibraryVisualMediaStates
import github.returdev.animemangavault.domain.library.anime.DeleteAnimeFromUserLibraryUseCase
import github.returdev.animemangavault.domain.library.anime.UpdateAnimeStateInUserLibraryUseCase
import github.returdev.animemangavault.domain.library.manga.DeleteMangaFromUserLibraryUseCase
import github.returdev.animemangavault.domain.library.manga.UpdateMangaStateInUserLibraryUseCase
import github.returdev.animemangavault.domain.model.reduced.ReducedAnime
import github.returdev.animemangavault.domain.model.reduced.ReducedManga
import github.returdev.animemangavault.domain.model.reduced.ReducedVisualMedia
import javax.inject.Inject

class UpdateVisualMediaInUserLibraryUseCase @Inject constructor(
    private val updateAnime : UpdateAnimeStateInUserLibraryUseCase,
    private val updateManga : UpdateMangaStateInUserLibraryUseCase,
    private val deleteAnime : DeleteAnimeFromUserLibraryUseCase,
    private val deleteManga : DeleteMangaFromUserLibraryUseCase
) {

    suspend operator fun invoke(reducedVM : ReducedVisualMedia, newState : UserLibraryVisualMediaStates?): Int {
        val id = reducedVM.id
        return when(reducedVM){
            is ReducedAnime -> {
                if (newState == null){
                    deleteAnime(id)
                }else{
                    updateAnime(id, newState)
                }
            }
            is ReducedManga -> {
                if (newState == null){
                    deleteManga(id)
                }else{
                    updateManga(id, newState)
                }
            }
        }

    }

}