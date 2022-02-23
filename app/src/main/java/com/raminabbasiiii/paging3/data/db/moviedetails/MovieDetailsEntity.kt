package com.raminabbasiiii.paging3.data.db.moviedetails

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_movie_details")
data class MovieDetailsEntity(
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

    @ColumnInfo(name = "rated")
    val rated: String,

    @ColumnInfo(name = "released")
    val released: String,

    @ColumnInfo(name = "runtime")
    val runtime: String,

    @ColumnInfo(name = "director")
    val director: String,

    @ColumnInfo(name = "writer")
    val writer: String,

    @ColumnInfo(name = "actors")
    val actors: String,

    @ColumnInfo(name = "plot")
    val plot: String,

    @ColumnInfo(name = "awards")
    val awards: String,

    @ColumnInfo(name = "imdb_votes")
    val votes: String,

    @ColumnInfo(name = "genres")
    val genres: List<String>? = null,

    @ColumnInfo(name = "images")
    val images: List<String>? = null
)
