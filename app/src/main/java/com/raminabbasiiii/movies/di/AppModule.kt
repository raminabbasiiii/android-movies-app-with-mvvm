package com.raminabbasiiii.movies.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.raminabbasiiii.movies.data.db.AppDatabase
import com.raminabbasiiii.movies.data.network.Api
import com.raminabbasiiii.movies.repository.MovieRepository
import com.raminabbasiiii.movies.repository.MovieRepositoryImpl
import com.raminabbasiiii.movies.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(gsonBuilder: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
    }

    @Singleton
    @Provides
    fun provideApiService(retrofitBuilder: Retrofit.Builder): Api {
        return retrofitBuilder
            .build()
            .create(Api::class.java)
    }

    @Singleton
    @Provides
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room
            .databaseBuilder(app, AppDatabase::class.java, Constants.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(
        database: AppDatabase
    ) = database.movieDao()

    @Singleton
    @Provides
    fun provideRemoteKeyDao(
        database: AppDatabase
    ) = database.remoteKeyDao()

    @Singleton
    @Provides
    fun provideMovieRepository(
        api: Api,
        database: AppDatabase
    ) = MovieRepositoryImpl(api,database) as MovieRepository
}