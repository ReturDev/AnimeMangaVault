package github.returdev.animemangavault.ui.model.filters.core

import github.returdev.animemangavault.ui.model.reduced.ReducedAnimeUi
import github.returdev.animemangavault.ui.model.reduced.ReducedMangaUi
import github.returdev.animemangavault.ui.model.reduced.ReducedVisualMediaUi

enum class VisualMediaTypes {

    ANIME,
    MANGA;

    override fun toString(): String {
        return super.toString().lowercase().replaceFirstChar { it.uppercaseChar() }
    }

    companion object{
        fun getType(visualMediaUi: ReducedVisualMediaUi): VisualMediaTypes {
            return when(visualMediaUi){
                is ReducedAnimeUi -> ANIME
                is ReducedMangaUi -> MANGA
            }
        }
    }

}