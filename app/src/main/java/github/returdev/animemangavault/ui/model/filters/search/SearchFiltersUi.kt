package github.returdev.animemangavault.ui.model.filters.search

import github.returdev.animemangavault.ui.model.components.anime.AnimeStatus
import github.returdev.animemangavault.ui.model.components.anime.AnimeTypes
import github.returdev.animemangavault.ui.model.components.common.Genres
import github.returdev.animemangavault.ui.model.components.manga.MangaStatus
import github.returdev.animemangavault.ui.model.components.manga.MangaTypes
import github.returdev.animemangavault.ui.model.filters.core.SortDirectionUi

sealed class SearchFiltersUi {

    abstract val type : Any?
    abstract val status : Any?
    abstract val genres : List<Genres>
    abstract val orderBy : Any?
    abstract val sort : SortDirectionUi

    data class AnimeFiltersUi(
        override val type: AnimeTypes? = null,
        override val status: AnimeStatus? = null,
        override val genres: List<Genres> = emptyList(),
        override val orderBy: AnimeOrderByUi? = null,
        override val sort: SortDirectionUi = SortDirectionUi.ASCENDANT
    ) : SearchFiltersUi()

    data class MangaFiltersUi(
        override val type: MangaTypes? = null,
        override val status: MangaStatus? = null,
        override val genres: List<Genres> = emptyList(),
        override val orderBy: MangaOrderByUi? = null,
        override val sort: SortDirectionUi = SortDirectionUi.ASCENDANT
    ) : SearchFiltersUi()

}