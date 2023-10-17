package github.returdev.animemangavault.core.extensions

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
import github.returdev.animemangavault.ui.model.filters.search.AnimeOrderByUi
import github.returdev.animemangavault.ui.model.filters.search.MangaOrderByUi
import github.returdev.animemangavault.ui.model.filters.search.SearchFiltersUi

fun SortDirectionUi.toSortDirection(): SortDirection {
    return when(this){
        SortDirectionUi.ASCENDANT -> SortDirection.ASCENDANT
        SortDirectionUi.DESCENDANT -> SortDirection.DESCENDANT
    }
}

fun AnimeTypes.toAnimeTypeFilters() : AnimeTypeFilters? {

    return when(this){
        AnimeTypes.TV -> AnimeTypeFilters.TV
        AnimeTypes.MOVIE -> AnimeTypeFilters.MOVIE
        AnimeTypes.OVA -> AnimeTypeFilters.OVA
        AnimeTypes.SPECIAL -> AnimeTypeFilters.SPECIAL
        AnimeTypes.ONA -> AnimeTypeFilters.ONA
        AnimeTypes.MUSIC -> AnimeTypeFilters.MUSIC
        AnimeTypes.UNKNOWN -> null
    }

}

fun MangaTypes.toMangaTypeFilters() : MangaTypeFilters? {

    return when(this){
        MangaTypes.MANGA -> MangaTypeFilters.MANGA
        MangaTypes.NOVEL -> MangaTypeFilters.NOVEL
        MangaTypes.LIGHTNOVEL -> MangaTypeFilters.LIGHT_NOVEL
        MangaTypes.ONESHOT -> MangaTypeFilters.ONESHOT
        MangaTypes.DOUJIN -> MangaTypeFilters.DOUJIN
        MangaTypes.MANHWA -> MangaTypeFilters.MANHWA
        MangaTypes.MANHUA -> MangaTypeFilters.MANHUA
        MangaTypes.UNKNOWN -> null
    }

}

fun AnimeStatus.toAnimeStatusFilters() : AnimeStatusFilters {

    return when(this){
        AnimeStatus.CURRENTLY_AIRING -> AnimeStatusFilters.AIRING
        AnimeStatus.FINISHED_AIRING -> AnimeStatusFilters.COMPLETE
        AnimeStatus.NOT_YET_AIRED -> AnimeStatusFilters.UPCOMING
    }

}

fun MangaStatus.toMangaStatusFilters() : MangaStatusFilters {

    return when(this){
        MangaStatus.PUBLISHING -> MangaStatusFilters.PUBLISHING
        MangaStatus.FINISHED -> MangaStatusFilters.COMPLETE
        MangaStatus.ON_HIATUS -> MangaStatusFilters.HIATUS
        MangaStatus.DISCONTINUED -> MangaStatusFilters.DISCONTINUED
        MangaStatus.UPCOMING -> MangaStatusFilters.UPCOMING
    }

}

fun Genres.toGenreFilters() : GenreFilters {

    return when(this){
        Genres.ACTION -> GenreFilters.ACTION
        Genres.ADVENTURE -> GenreFilters.ADVENTURE
        Genres.AVANT_GARDE -> GenreFilters.AVANT_GARDE
        Genres.AWARD_WINNING -> GenreFilters.AWARD_WINNING
        Genres.BOYS_LOVE -> GenreFilters.BOYS_LOVE
        Genres.COMEDY -> GenreFilters.COMEDY
        Genres.DRAMA -> GenreFilters.DRAMA
        Genres.ECCHI -> GenreFilters.ECCHI
        Genres.EROTICA -> GenreFilters.EROTICA
        Genres.FANTASY -> GenreFilters.FANTASY
        Genres.GOURMET -> GenreFilters.GOURMET
        Genres.GIRLS_LOVE -> GenreFilters.GIRLS_LOVE
        Genres.HENTAI -> GenreFilters.HENTAI
        Genres.HORROR -> GenreFilters.HORROR
        Genres.MYSTERY -> GenreFilters.MYSTERY
        Genres.ROMANCE -> GenreFilters.ROMANCE
        Genres.SCI_FI -> GenreFilters.SCI_FI
        Genres.SLICE_OF_LIFE -> GenreFilters.SLICE_OF_LIFE
        Genres.SPORTS -> GenreFilters.SPORTS
        Genres.SUPERNATURAL -> GenreFilters.SUPERNATURAL
        Genres.ANIME_SUSPENSE -> GenreFilters.ANIME_SUSPENSE
        Genres.MANGA_SUSPENSE -> GenreFilters.MANGA_SUSPENSE
    }

}

fun AnimeOrderByUi.toAnimeOrderByFilters() : AnimeOrderByFilters{

    return when(this){
        AnimeOrderByUi.TITLE -> AnimeOrderByFilters.TITLE
        AnimeOrderByUi.START_DATE -> AnimeOrderByFilters.START_DATE
        AnimeOrderByUi.END_DATE -> AnimeOrderByFilters.END_DATE
        AnimeOrderByUi.EPISODES -> AnimeOrderByFilters.EPISODES
        AnimeOrderByUi.SCORE -> AnimeOrderByFilters.SCORE
        AnimeOrderByUi.SCORED_BY -> AnimeOrderByFilters.SCORED_BY
        AnimeOrderByUi.RANK -> AnimeOrderByFilters.RANK
        AnimeOrderByUi.POPULARITY -> AnimeOrderByFilters.POPULARITY
        AnimeOrderByUi.FAVORITES -> AnimeOrderByFilters.FAVORITES
    }

}

fun MangaOrderByUi.toMangaOrderByFilters() : MangaOrderByFilters{

    return when(this){
        MangaOrderByUi.TITLE -> MangaOrderByFilters.TITLE
        MangaOrderByUi.START_DATE -> MangaOrderByFilters.START_DATE
        MangaOrderByUi.END_DATE -> MangaOrderByFilters.END_DATE
        MangaOrderByUi.CHAPTERS -> MangaOrderByFilters.CHAPTERS
        MangaOrderByUi.VOLUMES -> MangaOrderByFilters.VOLUMES
        MangaOrderByUi.SCORE -> MangaOrderByFilters.SCORE
        MangaOrderByUi.SCORED_BY -> MangaOrderByFilters.SCORED_BY
        MangaOrderByUi.RANK -> MangaOrderByFilters.RANK
        MangaOrderByUi.POPULARITY -> MangaOrderByFilters.POPULARITY
        MangaOrderByUi.FAVORITES -> MangaOrderByFilters.FAVORITES
    }

}

fun SearchFiltersUi.AnimeFiltersUi.toAnimeFilters() = SearchFilters.AnimeFilters(

    type = this.type?.toAnimeTypeFilters(),
    status = this.status?.toAnimeStatusFilters(),
    genres = this.genres.map { g -> g.toGenreFilters() },
    orderBy = this.orderBy?.toAnimeOrderByFilters(),
    sort = this.sort.toSortDirection()

)

fun SearchFiltersUi.MangaFiltersUi.toMangaFilters() = SearchFilters.MangaFilters(

    type = this.type?.toMangaTypeFilters(),
    status = this.status?.toMangaStatusFilters(),
    genres = this.genres.map { g -> g.toGenreFilters() },
    orderBy = this.orderBy?.toMangaOrderByFilters(),
    sort = this.sort.toSortDirection()

)