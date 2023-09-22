package github.returdev.animemangavault.data.cache.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import github.returdev.animemangavault.data.cache.dao.AnimeCacheDao
import github.returdev.animemangavault.data.cache.dao.MangaCacheDao
import github.returdev.animemangavault.data.cache.model.db.CacheDataBase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    private const val ROOM_CACHE_NAME = "anime_manga_cache"

    @Provides
    @Singleton
    fun provideCacheRoom(@ApplicationContext context : Context) : CacheDataBase {
        return Room.databaseBuilder(context, CacheDataBase::class.java, ROOM_CACHE_NAME).build()
    }

    @Provides
    @Singleton
    fun provideAnimeCacheDao(cache : CacheDataBase) : AnimeCacheDao {
        return cache.getAnimeCacheDao()
    }

    @Provides
    @Singleton
    fun provideMangaCacheDao(cache : CacheDataBase) : MangaCacheDao {
        return  cache.getMangaCacheDao()
    }

}