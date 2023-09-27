package github.returdev.animemangavault.ui.model.filters

import github.returdev.animemangavault.core.model.core.filters.SearchFilters
import github.returdev.animemangavault.core.model.core.filters.anime.AnimeOrderByFilters
import github.returdev.animemangavault.core.model.core.filters.anime.AnimeStatusFilters
import github.returdev.animemangavault.core.model.core.filters.anime.AnimeTypeFilters
import github.returdev.animemangavault.core.model.core.filters.common.GenreFilters
import github.returdev.animemangavault.core.model.core.filters.common.SortDirection
import github.returdev.animemangavault.core.model.core.filters.manga.MangaOrderByFilters
import github.returdev.animemangavault.core.model.core.filters.manga.MangaStatusFilters
import github.returdev.animemangavault.core.model.core.filters.manga.MangaTypeFilters
import github.returdev.animemangavault.ui.model.components.anime.AnimeStatus
import github.returdev.animemangavault.ui.model.components.anime.AnimeTypes
import github.returdev.animemangavault.ui.model.components.common.Genres
import github.returdev.animemangavault.ui.model.components.manga.MangaStatus
import github.returdev.animemangavault.ui.model.components.manga.MangaTypes
import github.returdev.animemangavault.ui.model.filters.core.SortDirectionUi
import github.returdev.animemangavault.ui.model.filters.search.AnimeOrderByProperties
import github.returdev.animemangavault.ui.model.filters.search.MangaOrderByProperties

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
        override val orderBy: AnimeOrderByProperties? = null,
        override val sort: SortDirectionUi = SortDirectionUi.ASCENDANT
    ) : SearchFiltersUi()

    data class MangaFiltersUi(
        override val type: MangaTypes? = null,
        override val status: MangaStatus? = null,
        override val genres: List<Genres> = emptyList(),
        override val orderBy: MangaOrderByProperties? = null,
        override val sort: SortDirectionUi = SortDirectionUi.ASCENDANT
    ) : SearchFiltersUi()

}