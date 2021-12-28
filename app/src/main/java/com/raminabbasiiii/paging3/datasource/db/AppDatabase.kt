package com.raminabbasiiii.paging3.datasource.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.raminabbasiiii.paging3.datasource.db.entity.Movie
import com.raminabbasiiii.paging3.datasource.db.entity.RemoteKey

@Database( entities = [Movie::class, RemoteKey::class], version = 2, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun movieDao() : MovieDao
    abstract fun remoteKeyDao() : RemoteKeyDao
}