package com.raminabbasiiii.paging3.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.raminabbasiiii.paging3.data.db.movie.MovieDao
import com.raminabbasiiii.paging3.data.db.movie.MovieEntity
import com.raminabbasiiii.paging3.data.db.moviedetails.MovieDetailsDao
import com.raminabbasiiii.paging3.data.db.moviedetails.MovieDetailsEntity
import com.raminabbasiiii.paging3.data.db.remotekey.RemoteKeyDao
import com.raminabbasiiii.paging3.data.db.remotekey.RemoteKey
import com.raminabbasiiii.paging3.util.Converters

@Database( entities = [MovieEntity::class, MovieDetailsEntity::class, RemoteKey::class],
    version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun movieDao() : MovieDao
    abstract fun remoteKeyDao() : RemoteKeyDao
    abstract fun movieDetailsDao() : MovieDetailsDao
}