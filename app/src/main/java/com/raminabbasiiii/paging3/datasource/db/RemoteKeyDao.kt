package com.raminabbasiiii.paging3.datasource.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raminabbasiiii.paging3.datasource.db.entity.RemoteKey

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKey(remoteKey: RemoteKey)

    @Query("DELETE FROM remote_keys")
    suspend fun deleteAllKeys()

    @Query("SELECT * FROM remote_keys")
    suspend fun getKeys():List<RemoteKey>
}