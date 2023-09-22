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
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheTestModule {


    @Provides
    @Singleton
    @Named("RoomTest")
    fun provideCacheRoom(@ApplicationContext context : Context) : CacheDataBase {
        return Room.inMemoryDatabaseBuilder(context, CacheDataBase::class.java).build()
    }

    @Provides
    @Singleton
    @Named("RoomTest")
    fun provideAnimeCacheDao(cache : CacheDataBase) : AnimeCacheDao {
        return cache.getAnimeCacheDao()
    }

    @Provides
    @Singleton
    @Named("RoomTest")
    fun provideMangaCacheDao(cache : CacheDataBase) : MangaCacheDao {
        return  cache.getMangaCacheDao()
    }

}