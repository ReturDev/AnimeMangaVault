package github.returdev.animemangavault.core.extensions

import github.returdev.animemangavault.core.model.components.Title
import github.returdev.animemangavault.domain.model.basic.BasicAnime
import github.returdev.animemangavault.domain.model.basic.BasicManga
import github.returdev.animemangavault.domain.model.full.FullAnime
import github.returdev.animemangavault.domain.model.full.FullManga
import github.returdev.animemangavault.domain.model.reduced.ReducedAnime
import github.returdev.animemangavault.domain.model.reduced.ReducedManga
import github.returdev.animemangavault.ui.model.basic.BasicAnimeUi
import github.returdev.animemangavault.ui.model.basic.BasicMangaUi
import github.returdev.animemangavault.ui.model.components.anime.AnimeDemographics
import github.returdev.animemangavault.ui.model.components.anime.AnimeStatus
import github.returdev.animemangavault.ui.model.components.anime.AnimeTypes
import github.returdev.animemangavault.ui.model.components.anime.Season
import github.returdev.animemangavault.ui.model.components.common.Genres
import github.returdev.animemangavault.ui.model.components.manga.MangaDemographics
import github.returdev.animemangavault.ui.model.components.manga.MangaStatus
import github.returdev.animemangavault.ui.model.components.manga.MangaTypes
import github.returdev.animemangavault.ui.model.full.FullAnimeUi
import github.returdev.animemangavault.ui.model.full.FullMangaUi
import github.returdev.animemangavault.ui.model.reduced.ReducedAnimeUi
import github.returdev.animemangavault.ui.model.reduced.ReducedMangaUi

//From Domain to Ui

fun FullAnime.toFullAnimeUi() = FullAnimeUi(
    id = this.id,
    type = AnimeTypes.getAnimeType(this.type),
    images = this.images,
    titles = this.titles,
    score = this.score,
    numberOfScorers = this.numberOfScorers,
    rank = this.rank,
    synopsis = this.synopsis,
    genres = this.genres.map { g -> Genres.valueOf(g) },
    demographics = this.demographics.map { d -> AnimeDemographics.valueOf(d)},
    source = this.source,
    episodes = this.episodes,
    status = AnimeStatus.getStatus(this.status),
    airing = this.airing,
    aired = this.aired,
    season = this.season?.let { Season.getSeason(it) }
)

fun FullManga.toFullMangaUi() = FullMangaUi(
    id = this.id,
    type = MangaTypes.getMangaType(this.type),
    images = this.images,
    titles = this.titles,
    score = this.score,
    numberOfScorers = this.numberOfScorers,
    rank = this.rank,
    synopsis = this.synopsis,
    status = MangaStatus.getStatus(this.status),
    genres = this.genres.map { g -> Genres.valueOf(g) },
    demographics = this.demographics.map { d -> MangaDemographics.valueOf(d) },
    chapters = this.chapters,
    volumes = this.volumes,
    publishing = this.publishing,
    published = this.published
)

fun BasicAnime.toBasicAnimeUi() = BasicAnimeUi(
    id = this.id,
    images = this.images,
    title = this.title,
    type = AnimeTypes.getAnimeType(this.type),
    genres = this.genres.map { g -> Genres.valueOf(g) },
    demographics = this.demographics.map { d -> AnimeDemographics.valueOf(d) },
    score = this.score
)

fun BasicManga.toBasicMangaUi() = BasicMangaUi(
    id = this.id,
    images = this.images,
    title = this.title,
    type = MangaTypes.getMangaType(this.type),
    genres = this.genres.map { g -> Genres.valueOf(g) },
    demographics = this.demographics.map { d -> MangaDemographics.valueOf(d) },
    score = this.score
)

fun ReducedAnime.toReducedAnimeUi() = ReducedAnimeUi(
    id = this.id,
    imageUrl = this.imageUrl,
    defaultTitle = this.defaultTitle,
    score = this.score
)

fun ReducedManga.toReducedMangaUi() = ReducedMangaUi(
    id = this.id,
    imageUrl = this.imageUrl,
    defaultTitle = this.defaultTitle,
    score = this.score
)


//From Ui to Domain

fun FullAnimeUi.toReducedAnime() = ReducedAnime(
    id = this.id,
    imageUrl = images[1].url,
    defaultTitle = titles.filterIsInstance<Title.DefaultTitle>().map { default -> default.title }.first(),
    score = this.score
)

fun FullMangaUi.toReducedManga() = ReducedManga(
    id = this.id,
    imageUrl = images[1].url,
    defaultTitle = titles.filterIsInstance<Title.DefaultTitle>().map { default -> default.title }.first(),
    score = this.score
)

fun BasicAnimeUi.toReducedAnime() = ReducedAnime(
    id = this.id,
    imageUrl = images[1].url,
    defaultTitle = title,
    score = this.score
)

fun BasicMangaUi.toReducedManga() = ReducedManga(
    id = this.id,
    imageUrl = images[1].url,
    defaultTitle = title,
    score = this.score
)

fun ReducedAnimeUi.toReducedAnime() = ReducedAnime(
    id = this.id,
    imageUrl = this.imageUrl,
    defaultTitle = this.defaultTitle,
    score = this.score
)

fun ReducedMangaUi.toReducedManga() = ReducedManga(
    id = this.id,
    imageUrl = this.imageUrl,
    defaultTitle = this.defaultTitle,
    score = this.score
)