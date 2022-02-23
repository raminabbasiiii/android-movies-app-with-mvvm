package com.raminabbasiiii.paging3.data.db.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_movie")
data class MovieEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "poster")
    val poster: String,

    @ColumnInfo(name = "year")
    val year: String,

    @ColumnInfo(name = "country")
    val country: String,

    @ColumnInfo(name = "rating")
    val rating: String,

    @ColumnInfo(name = "genres")
    val genres: List<String>? = null,

    @ColumnInfo(name = "images")
    val images: List<String>? = null
)