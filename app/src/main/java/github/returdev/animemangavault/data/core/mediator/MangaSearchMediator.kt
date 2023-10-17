package github.returdev.animemangavault.data.core.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.room.withTransaction
import github.returdev.animemangavault.core.annotation.IoDispatcher
import github.returdev.animemangavault.core.extensions.toMangaCacheEntity
import github.returdev.animemangavault.core.model.core.filters.SearchFilters
import github.returdev.animemangavault.core.network.NetworkConnectivity
import github.returdev.animemangavault.data.api.repository.MangaApiRepository
import github.returdev.animemangavault.data.cache.dao.MangaCacheDao
import github.returdev.animemangavault.data.cache.model.db.CacheDataBase
import github.returdev.animemangavault.data.cache.model.entity.MangaCacheEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class MangaSearchMediator constructor(
    private val title : String,
    private val filters : SearchFilters.MangaFilters,
    private val networkConnectivity : NetworkConnectivity,
    private val cacheDataBase : CacheDataBase,
    private val mangaCacheDao : MangaCacheDao,
    private val mangaApiRepository : MangaApiRepository,
    @IoDispatcher dispatcher : CoroutineDispatcher
) : VisualMediaMediator<MangaCacheEntity>(dispatcher) {

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getApiDataAndSaveOnCache(
        apiPage: Int,
        loadType: LoadType
    ): MediatorResult {

        return try {

            val response = withContext(dispatcher){
                mangaApiRepository.getMangaSearch(
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
                    mangaCacheDao.clearTable()
                }

                mangaCacheDao.insertPage(
                    response.data.map {
                            mangaApiData -> mangaApiData.toMangaCacheEntity()
                    }
                )

            }

            MediatorResult.Success(endOfPaginationReached)

        } catch (e: Exception) {

            MediatorResult.Error(e)

        }


    }


}