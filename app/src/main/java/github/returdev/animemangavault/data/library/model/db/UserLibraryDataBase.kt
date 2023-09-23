package github.returdev.animemangavault.data.library.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import github.returdev.animemangavault.data.library.dao.AnimeLibraryDao
import github.returdev.animemangavault.data.library.dao.MangaLibraryDao
import github.returdev.animemangavault.data.library.model.converter.DateConverter
import github.returdev.animemangavault.data.library.model.entity.AnimeLibraryEntity
import github.returdev.animemangavault.data.library.model.entity.MangaLibraryEntity

/**
 * Room database class that defines the database and its associated DAOs.
 */
@Database(entities = [AnimeLibraryEntity::class, MangaLibraryEntity::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class UserLibraryDataBase : RoomDatabase() {
    /**
     * Retrieves the DAO for accessing anime-related data in the library.
     *
     * @return The DAO for anime library data.
     */
    abstract fun getAnimeLibraryDao() : AnimeLibraryDao

    /**
     * Retrieves the DAO for accessing manga-related data in the library.
     *
     * @return The DAO for manga library data.
     */
    abstract fun getMangaLibraryDao() : MangaLibraryDao
}