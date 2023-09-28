package github.returdev.animemangavault.data.cache.repository.implementation

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import androidx.sqlite.db.SimpleSQLiteQuery
import github.returdev.animemangavault.core.annotation.IoDispatcher
import github.returdev.animemangavault.core.extensions.toBasicAnime
import github.returdev.animemangavault.core.model.core.filters.SearchFilters
import github.returdev.animemangavault.core.network.NetworkConnectivity
import github.returdev.animemangavault.data.api.repository.implementation.AnimeApiRepositoryImpl
import github.returdev.animemangavault.data.cache.dao.AnimeCacheDao
import github.returdev.animemangavault.data.cache.model.db.CacheDataBase
import github.returdev.animemangavault.data.cache.repository.AnimeCacheRepository
import github.returdev.animemangavault.data.cache.repository.core.CacheRepositoryUtil
import github.returdev.animemangavault.data.core.mediator.AnimeSearchMediator
import github.returdev.animemangavault.domain.model.basic.BasicAnime
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of [AnimeCacheRepository] that handles caching of anime data.
 *
 * @param networkConnectivity The network connectivity manager.
 * @param cacheDataBase The Room database for caching.
 * @param animeCacheDao The Data Access Object (DAO) for anime cache operations.
 * @param animeApiRepository The repository for making network requests to fetch anime data.
 * @param dispatcher The CoroutineDispatcher for background operations.
 */
@OptIn(ExperimentalPagingApi::class)
@Singleton
class AnimeCacheRepositoryImpl @Inject constructor(
    private val networkConnectivity: NetworkConnectivity,
    private val cacheDataBase: CacheDataBase,
    private val animeCacheDao: AnimeCacheDao,
    private val animeApiRepository: AnimeApiRepositoryImpl,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : AnimeCacheRepository {

    /**
     * Retrieves Anime data from the cache database as a [Flow] of [PagingData] of [BasicAnime].
     *
     * @param title The title used for searching Anime data.
     * @param filters The filters used for refining the Anime data.
     * @return A [Flow] of [PagingData] containing [BasicAnime] objects.
     */
    override fun getAnimeSearch(title: String, filters: SearchFilters.AnimeFilters): Flow<PagingData<BasicAnime>> {
        return Pager(
            config = CacheRepositoryUtil.getPagerConfig(),
            remoteMediator = AnimeSearchMediator(
                title,
                filters,
                networkConnectivity,
                cacheDataBase,
                animeCacheDao,
                animeApiRepository,
                dispatcher
            ),

            pagingSourceFactory = {
                animeCacheDao.getAnime()
            }
        ).flow.map { pagingData -> pagingData.map { anime -> anime.toBasicAnime() } }
    }


    /**
     * Clears all Anime data from the cache database and resets the primary key auto-increment value.
     */
    override fun clearAndResetPrimaryKey() {
        animeCacheDao.clearTable()
        animeCacheDao.executeRawQuery(
            SimpleSQLiteQuery(
                query = CacheRepositoryUtil.RESET_PRIMARY_KEY_AUTO_G,
                bindArgs = arrayOf("anime_cache")
            )
        )
    }

}