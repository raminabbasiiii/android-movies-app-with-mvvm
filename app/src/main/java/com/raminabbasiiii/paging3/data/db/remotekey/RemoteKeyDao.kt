package com.raminabbasiiii.paging3.data.db.remotekey

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKey(remoteKey: RemoteKey)

    @Query("DELETE FROM remote_keys")
    suspend fun deleteAllKeys()

    @Query("SELECT * FROM remote_keys")
    suspend fun getKeys():List<RemoteKey>
}