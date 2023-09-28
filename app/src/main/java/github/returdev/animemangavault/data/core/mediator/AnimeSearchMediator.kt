package github.returdev.animemangavault.data.core.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.room.withTransaction
import github.returdev.animemangavault.core.annotation.IoDispatcher
import github.returdev.animemangavault.core.extensions.toAnimeCacheEntity
import github.returdev.animemangavault.core.model.core.filters.SearchFilters
import github.returdev.animemangavault.core.network.NetworkConnectivity
import github.returdev.animemangavault.data.api.repository.implementation.AnimeApiRepositoryImpl
import github.returdev.animemangavault.data.cache.dao.AnimeCacheDao
import github.returdev.animemangavault.data.cache.model.db.CacheDataBase
import github.returdev.animemangavault.data.cache.model.entity.AnimeCacheEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext


class AnimeSearchMediator constructor(
    private val title : String,
    private val filters: SearchFilters.AnimeFilters,
    private val networkConnectivity : NetworkConnectivity,
    private val cacheDataBase: CacheDataBase,
    private val animeCacheDao: AnimeCacheDao,
    private val animeApiRepository: AnimeApiRepositoryImpl,
    @IoDispatcher dispatcher : CoroutineDispatcher
) : VisualMediaSearchMediator<AnimeCacheEntity>(dispatcher) {

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getApiDataAndSaveOnCache(
        apiPage: Int,
        loadType: LoadType
    ): MediatorResult {

        return try {

            val response = withContext(dispatcher){
                animeApiRepository.getAnimeSortSearch(
                    page = apiPage,
                    title = title,
                    filters = filters,
                    networkState = networkConnectivity.networkState
                )
            }
            val endOfPaginationReached = response.data.isEmpty()


            hasNextPage = response.pagination.hasNextPage

            cacheDataBase.withTransaction {

                if (loadType == LoadType.REFRESH) {
                    animeCacheDao.clearTable()
                }

                animeCacheDao.insertPage(
                    response.data.map {
                            animeApiData -> animeApiData.toAnimeCacheEntity()
                    }
                )

            }

            MediatorResult.Success(endOfPaginationReached)

        } catch (e: Exception) {

            MediatorResult.Error(e)

        }

    }
}