package com.raminabbasiiii.paging3.data.db.remotekey

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKey(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val nextKey: Int?,
    val isEndReached: Boolean
)
