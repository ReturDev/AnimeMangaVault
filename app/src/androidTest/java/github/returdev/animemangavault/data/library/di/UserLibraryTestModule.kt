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
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserLibraryTestModule {

    @Provides
    @Singleton
    @Named("UserLibraryTest")
    fun provideUserLibraryDataBase(@ApplicationContext context : Context) : UserLibraryDataBase {
        return Room.inMemoryDatabaseBuilder(context, UserLibraryDataBase::class.java).build()
    }

    @Provides
    @Singleton
    @Named("UserLibraryTest")
    fun provideAnimeLibraryDao(libraryDataBase: UserLibraryDataBase) : AnimeLibraryDao {
        return libraryDataBase.getAnimeLibraryDao()
    }

    @Provides
    @Singleton
    @Named("UserLibraryTest")
    fun provideMangaLibraryDao(libraryDataBase: UserLibraryDataBase): MangaLibraryDao {
        return libraryDataBase.getMangaLibraryDao()
    }

}