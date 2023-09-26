package github.returdev.animemangavault.data.cache.repository.implementation

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import androidx.sqlite.db.SimpleSQLiteQuery
import github.returdev.animemangavault.core.annotation.IoDispatcher
import github.returdev.animemangavault.core.extensions.toBasicManga
import github.returdev.animemangavault.core.model.core.filters.Filters
import github.returdev.animemangavault.core.network.NetworkConnectivity
import github.returdev.animemangavault.data.api.repository.AnimeApiRepository
import github.returdev.animemangavault.data.api.repository.MangaApiRepository
import github.returdev.animemangavault.data.cache.dao.AnimeCacheDao
import github.returdev.animemangavault.data.cache.dao.MangaCacheDao
import github.returdev.animemangavault.data.cache.model.db.CacheDataBase
import github.returdev.animemangavault.data.cache.repository.MangaCacheRepository
import github.returdev.animemangavault.data.cache.repository.core.CacheRepositoryUtil
import github.returdev.animemangavault.data.core.mediator.MangaSearchMediator
import github.returdev.animemangavault.domain.model.basic.BasicManga
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Implementation of [MangaCacheRepository] that handles caching of manga data.
 *
 * @param networkConnectivity The network connectivity manager.
 * @param cacheDataBase The Room database for caching.
 * @param mangaCacheDao The Data Access Object (DAO) for manga cache operations.
 * @param mangaApiRepository The repository for making network requests to fetch manga data.
 * @param dispatcher The CoroutineDispatcher for background operations.
 */
@OptIn(ExperimentalPagingApi::class)
class MangaCacheRepositoryImpl @Inject constructor(
    private val networkConnectivity: NetworkConnectivity,
    private val cacheDataBase: CacheDataBase,
    private val mangaCacheDao: MangaCacheDao,
    private val mangaApiRepository: MangaApiRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : MangaCacheRepository {

    /**
     * Retrieves Manga data from the cache database as a [Flow] of [PagingData] of [BasicManga].
     *
     * @param title The title used for searching Manga data.
     * @param filters The filters used for refining the Manga data.
     * @return A [Flow] of [PagingData] containing [BasicManga] objects.
     */
    override fun getMangaSearch(title: String, filters: Filters): Flow<PagingData<BasicManga>> {
        return Pager(
            config = CacheRepositoryUtil.getPagerConfig(),
            remoteMediator = MangaSearchMediator(
                title = title,
                filters = filters,
                networkConnectivity = networkConnectivity,
                cacheDataBase = cacheDataBase,
                mangaCacheDao = mangaCacheDao,
                mangaApiRepository = mangaApiRepository,
                dispatcher = dispatcher
            ),
            pagingSourceFactory = {
                mangaCacheDao.getManga()
            }
        ).flow.map { data -> data.map { manga -> manga.toBasicManga() } }
    }

    /**
     * Clears all Manga data from the cache database.
     */
    override fun clearCache() {
        mangaCacheDao.clearTable()
    }

    /**
     * Clears all Manga data from the cache database and resets the primary key auto-increment value.
     */
    override fun clearAndResetPrimaryKey() {
        clearCache()
        mangaCacheDao.executeRawQuery(
            SimpleSQLiteQuery(
                query = CacheRepositoryUtil.RESET_PRIMARY_KEY_AUTO_G,
                bindArgs = arrayOf("anime_cache")
            )
        )
    }


}