package github.returdev.animemangavault.core.extensions

import android.icu.util.Calendar
import github.returdev.animemangavault.core.model.components.ImageUrl
import github.returdev.animemangavault.core.model.components.Released
import github.returdev.animemangavault.core.model.components.Title
import github.returdev.animemangavault.core.model.library.UserLibraryVisualMediaStates
import github.returdev.animemangavault.data.api.model.anime.AnimeApiResponse
import github.returdev.animemangavault.data.api.model.anime.AnimeSearchApiResponse
import github.returdev.animemangavault.data.api.model.components.DataImageResponseComponent
import github.returdev.animemangavault.data.api.model.components.DataReleasedResponseComponent
import github.returdev.animemangavault.data.api.model.manga.MangaApiResponse
import github.returdev.animemangavault.data.api.model.manga.MangaSearchApiResponse
import github.returdev.animemangavault.data.cache.model.entity.AnimeCacheEntity
import github.returdev.animemangavault.data.cache.model.entity.MangaCacheEntity
import github.returdev.animemangavault.data.library.model.entity.AnimeLibraryEntity
import github.returdev.animemangavault.data.library.model.entity.MangaLibraryEntity
import github.returdev.animemangavault.domain.model.basic.BasicAnime
import github.returdev.animemangavault.domain.model.basic.BasicManga
import github.returdev.animemangavault.domain.model.full.FullAnime
import github.returdev.animemangavault.domain.model.full.FullManga
import github.returdev.animemangavault.domain.model.reduced.ReducedAnime
import github.returdev.animemangavault.domain.model.reduced.ReducedManga
import java.util.Date

/***** Data to Domain *****/

fun AnimeApiResponse.ApiAnimeExtendedDataResponse.toFullAnime() = FullAnime(
        id = this.id,
        type = this.type,
        images = this.images.toImageUrls(),
        titles = this.titles.mapNotNull { title -> Title.createTitleByKey(title.type, title.title) },
        score = this.score,
        numberOfScorers = this.numberOfScorers,
        rank = this.rank,
        synopsis = this.synopsis.orEmpty(),
        genres = this.genres.map { g -> g.id },
        demographics = this.demographics.map { d -> d.id },
        source = this.source,
        episodes = this.episodes,
        status = this.status,
        airing = this.airing,
        aired = this.aired.toReleased(),
        season = this.season
    )


fun MangaApiResponse.ApiMangaExtendedDataResponse.toFullManga() = FullManga(
    id = this.id,
    type = this.type,
    images = this.images.toImageUrls(),
    titles = this.titles.mapNotNull { title -> Title.createTitleByKey(title.type, title.title) },
    score = this.score,
    numberOfScorers = this.numberOfScorers,
    rank = this.rank,
    synopsis = this.synopsis.orEmpty(),
    status = this.status,
    genres = this.genres.map { g -> g.id },
    demographics = this.demographics.map { d -> d.id },
    chapters = this.chapters,
    volumes = this.volumes,
    publishing = this.publishing,
    published = this.published.toReleased()
)

fun AnimeSearchApiResponse.ApiAnimeReducedDataResponse.toAnimeCacheEntity() = AnimeCacheEntity(
    id = this.id,
    images = this.images.toImageUrls().map { it.url },
    title = this.title,
    type = this.type ?: "",
    genres = this.genres.map { g -> g.id },
    demographics = this.demographics.map { d -> d.id },
    score = this.score
)

fun MangaSearchApiResponse.ApiMangaReducedDataResponse.toMangaCacheEntity() = MangaCacheEntity(
    id = this.id,
    images = this.images.toImageUrls().map { it.url },
    title = this.title,
    type = this.type ?: "",
    genres = this.genres.map { g -> g.id },
    demographics = this.demographics.map { d -> d.id },
    score = this.score
)

fun AnimeSearchApiResponse.ApiAnimeReducedDataResponse.toReducedAnimeUi() = ReducedAnime(
    id = this.id,
    imageUrl = images.toImageUrls()[1].url,
    defaultTitle = this.title,
    score = this.score
)

fun MangaSearchApiResponse.ApiMangaReducedDataResponse.toReducedMangaUi() = ReducedManga(
    id = this.id,
    imageUrl = images.toImageUrls()[1].url,
    defaultTitle = this.title,
    score = this.score
)

fun AnimeLibraryEntity.toReducedAnimeUi() = ReducedAnime(
    id = this.id,
    imageUrl = this.imageUrl,
    defaultTitle = this.defaultTitle,
    score = this.score
)

fun MangaLibraryEntity.toReducedMangaUi() = ReducedManga(
    id = this.id,
    imageUrl = this.imageUrl,
    defaultTitle = this.defaultTitle,
    score = this.score
)

fun AnimeCacheEntity.toBasicAnime() = BasicAnime(
    id = this.id,
    images = listOf(
        ImageUrl.SmallImageUrl(this.images[0]),
        ImageUrl.NormalImageUrl(this.images[1]),
        ImageUrl.LargeImageUrl(this.images[2])
    ),
    title = this.title,
    score = this.score,
    type = this.type,
    genres = this.genres,
    demographics = this.demographics
)

fun MangaCacheEntity.toBasicManga() = BasicManga(
    id = this.id,
    images = listOf(
        ImageUrl.SmallImageUrl(this.images[0]),
        ImageUrl.NormalImageUrl(this.images[1]),
        ImageUrl.LargeImageUrl(this.images[2])
    ),
    title = this.title,
    score = this.score,
    type = this.type,
    genres = this.genres,
    demographics = this.demographics
)

fun DataImageResponseComponent.toImageUrls() = listOf(

    ImageUrl.SmallImageUrl(this.webpFormat.smallImageUrl),
    ImageUrl.NormalImageUrl(this.webpFormat.normalImageUrl),
    ImageUrl.LargeImageUrl(this.webpFormat.largeImageUrl)

)

fun DataReleasedResponseComponent.toReleased() : Released {

    val startDate = this.date.startDate
    val endDate = this.date.endDate

    val from = Calendar.getInstance()
    from.set(startDate.year,startDate.month, startDate.day)

    val to = endDate?.let { Calendar.getInstance()}
    to?.set(endDate.year, endDate.month, endDate.day)

    return Released(from, to)

}


/***** Domain to Data *****/

fun ReducedAnime.toAnimeLibraryEntity(state: UserLibraryVisualMediaStates) = AnimeLibraryEntity(
    id = this.id,
    imageUrl = this.imageUrl,
    defaultTitle = this.defaultTitle,
    score = this.score,
    state = state.name,
    addedDate = Date()
)

fun ReducedManga.toMangaLibraryEntity(state: UserLibraryVisualMediaStates) = MangaLibraryEntity (
    id = this.id,
    imageUrl = this.imageUrl,
    defaultTitle = this.defaultTitle,
    score = this.score,
    state = state.name,
    addedDate = Date()
)