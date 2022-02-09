package com.raminabbasiiii.paging3.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.raminabbasiiii.paging3.datasource.db.AppDatabase
import com.raminabbasiiii.paging3.datasource.db.MovieDao
import com.raminabbasiiii.paging3.datasource.db.RemoteKeyDao
import com.raminabbasiiii.paging3.datasource.network.Api
import com.raminabbasiiii.paging3.repository.MainRepository
import com.raminabbasiiii.paging3.repository.inDb.InDbRepository
import com.raminabbasiiii.paging3.repository.inMemory.InMemoryRepository
import com.raminabbasiiii.paging3.util.Constants
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
    fun provideInDbRepository(
        api: Api,
        database: AppDatabase
    ) = InDbRepository(api,database) as MainRepository

    /*@Singleton
    @Provides
    fun provideInMemoryRepository(
        api: Api
    ) = InMemoryRepository(api) as MainRepository*/
}