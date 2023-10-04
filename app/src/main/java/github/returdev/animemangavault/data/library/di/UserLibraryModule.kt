package github.returdev.animemangavault.data.library.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import github.returdev.animemangavault.data.library.dao.AnimeLibraryDao
import github.returdev.animemangavault.data.library.dao.MangaLibraryDao
import github.returdev.animemangavault.data.library.model.db.UserLibraryDataBase
import github.returdev.animemangavault.data.library.repository.AnimeLibraryRepository
import github.returdev.animemangavault.data.library.repository.MangaLibraryRepository
import github.returdev.animemangavault.data.library.repository.implementation.AnimeLibraryRepositoryImpl
import github.returdev.animemangavault.data.library.repository.implementation.MangaLibraryRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserLibraryModule {

    private const val USER_LIBRARY_DATABASE_NAME = "visual_media_library"

    @Provides
    @Singleton
    fun provideUserLibraryDB(@ApplicationContext context: Context) : UserLibraryDataBase {
        return Room.databaseBuilder(context, UserLibraryDataBase::class.java, USER_LIBRARY_DATABASE_NAME).build()
    }

    @Provides
    @Singleton
    fun provideAnimeLibraryDao(database: UserLibraryDataBase): AnimeLibraryDao {
        return database.getAnimeLibraryDao()
    }

    @Provides
    @Singleton
    fun provideMangaLibraryDao(database: UserLibraryDataBase) : MangaLibraryDao {
        return database.getMangaLibraryDao()
    }

    @Provides
    @Singleton
    fun provideAnimeLibraryRepository(animeLibraryRepository: AnimeLibraryRepositoryImpl): AnimeLibraryRepository {
        return animeLibraryRepository
    }

    @Provides
    @Singleton
    fun provideMangaLibraryRepository(mangaLibraryRepository: MangaLibraryRepositoryImpl) : MangaLibraryRepository {
        return mangaLibraryRepository
    }

}