package github.returdev.animemangavault.data.cache.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import github.returdev.animemangavault.core.model.core.filters.Filters
import github.returdev.animemangavault.data.api.service.ApiService
import github.returdev.animemangavault.data.cache.model.entity.AnimeCacheEntity
import github.returdev.animemangavault.data.core.mediator.AnimeSearchMediator
import github.returdev.animemangavault.domain.model.basic.BasicAnime
import kotlinx.coroutines.flow.Flow

interface AnimeCacheRepository {

    fun getAnimeSearch(title : String, filters: Filters): Flow<PagingData<BasicAnime>>

    fun clearCache()

    fun clearAndResetPrimaryKey()

}