package github.returdev.animemangavault.core.model.core.filters

import github.returdev.animemangavault.core.model.core.filters.anime.AnimeOrderByFilters
import github.returdev.animemangavault.core.model.core.filters.anime.AnimeStatusFilters
import github.returdev.animemangavault.core.model.core.filters.anime.AnimeTypeFilters
import github.returdev.animemangavault.core.model.core.filters.common.GenreFilters
import github.returdev.animemangavault.core.model.core.filters.common.SortDirection
import github.returdev.animemangavault.core.model.core.filters.manga.MangaOrderByFilters
import github.returdev.animemangavault.core.model.core.filters.manga.MangaStatusFilters
import github.returdev.animemangavault.core.model.core.filters.manga.MangaTypeFilters


/**
 * Data class representing filters for querying anime or manga.
 *
 * @property type The type of anime or manga.
 * @property status The status of anime or manga.
 * @property genres The list of genre IDs.
 * @property orderBy The property used for ordering.
 * @property sort The direction of sorting (ascending or descending).
 */

sealed class SearchFilters{

    abstract val type : Any?
    abstract val status : Any?
    abstract val genres : List<GenreFilters>
    abstract val orderBy : Any?
    abstract val sort : SortDirection

    data class AnimeFilters(
        override val type: AnimeTypeFilters? = null,
        override val status: AnimeStatusFilters? = null,
        override val genres: List<GenreFilters> = emptyList(),
        override val orderBy: AnimeOrderByFilters? = null,
        override val sort: SortDirection = SortDirection.ASCENDANT
    ) : SearchFilters()

    data class MangaFilters(
        override val type: MangaTypeFilters? = null,
        override val status: MangaStatusFilters? = null,
        override val genres: List<GenreFilters> = emptyList(),
        override val orderBy: MangaOrderByFilters? = null,
        override val sort: SortDirection = SortDirection.ASCENDANT
    ) : SearchFilters()
}