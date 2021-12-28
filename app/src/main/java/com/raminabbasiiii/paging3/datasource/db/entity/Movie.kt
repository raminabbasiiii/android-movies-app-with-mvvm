package com.raminabbasiiii.paging3.datasource.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "tbl_movie")
data class Movie(

    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    val poster: String,
    val year: String,
    val country: String
)