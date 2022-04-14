package com.raminabbasiiii.movies.di

import android.content.Context
import androidx.room.Room
import com.raminabbasiiii.movies.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Named("test_db")
    fun provideInMemoryDatabase(@ApplicationContext context: Context) =
        Room.
        inMemoryDatabaseBuilder(context,AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
}