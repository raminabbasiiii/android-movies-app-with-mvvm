package com.raminabbasiiii.paging3.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.raminabbasiiii.paging3.data.db.movie.MovieDao
import com.raminabbasiiii.paging3.data.db.movie.MovieEntity
import com.raminabbasiiii.paging3.data.db.remotekey.RemoteKeyDao
import com.raminabbasiiii.paging3.data.db.remotekey.RemoteKey

@Database( entities = [MovieEntity::class, RemoteKey::class], version = 2, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun movieDao() : MovieDao
    abstract fun remoteKeyDao() : RemoteKeyDao
}