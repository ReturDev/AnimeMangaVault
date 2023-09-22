package github.returdev.animemangavault.data.cache.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import github.returdev.animemangavault.data.cache.dao.AnimeCacheDao
import github.returdev.animemangavault.data.cache.dao.MangaCacheDao
import github.returdev.animemangavault.data.cache.model.converter.StringListConverter
import github.returdev.animemangavault.data.cache.model.entity.AnimeCacheEntity
import github.returdev.animemangavault.data.cache.model.entity.MangaCacheEntity

/**
 * Cache database class that extends RoomDatabase and defines the entities and version.
 */
@Database(entities = [AnimeCacheEntity::class, MangaCacheEntity::class], version = 1)
@TypeConverters(StringListConverter::class)
abstract class CacheDataBase : RoomDatabase() {


    /**
     * Retrieves the Data Access Object (DAO) for anime cache.
     *
     * @return The [AnimeCacheDao] object.
     */
    abstract fun getAnimeCacheDao(): AnimeCacheDao

    /**
     * Retrieves the Data Access Object (DAO) for manga cache.
     *
     * @return The [MangaCacheDao] object.
     */
    abstract fun getMangaCacheDao(): MangaCacheDao
}