package github.returdev.animemangavault.data.api.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import github.returdev.animemangavault.BuildConfig.BASE_API_URL
import github.returdev.animemangavault.data.api.repository.AnimeApiRepository
import github.returdev.animemangavault.data.api.repository.MangaApiRepository
import github.returdev.animemangavault.data.api.repository.implementation.AnimeApiRepositoryImpl
import github.returdev.animemangavault.data.api.repository.implementation.MangaApiRepositoryImpl
import github.returdev.animemangavault.data.api.service.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Duration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private const val MAX_TIMEOUT_SECONDS = 3L

    @Provides
    @Singleton
    fun provideRetrofit(client : OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {

        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(Duration.ofSeconds(MAX_TIMEOUT_SECONDS))
            .readTimeout(Duration.ofSeconds(MAX_TIMEOUT_SECONDS))
            .build()

    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) : ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAnimeApiRepository(animeApiRepositoryImpl: AnimeApiRepositoryImpl) : AnimeApiRepository{
        return animeApiRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideMangaApiRepository(mangaApiRepositoryImpl: MangaApiRepositoryImpl) : MangaApiRepository{
        return mangaApiRepositoryImpl
    }

}