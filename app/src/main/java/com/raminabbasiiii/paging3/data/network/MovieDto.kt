package com.raminabbasiiii.paging3.data.network

import com.google.gson.annotations.SerializedName
import com.raminabbasiiii.paging3.data.db.movie.MovieEntity
import com.raminabbasiiii.paging3.model.Movie

class MovieDto(

    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("poster")
    val poster: String,

    @SerializedName("year")
    val year: String,

    @SerializedName("country")
    val country: String,

    @SerializedName("imdb_rating")
    val rating: String,

    @SerializedName("genres")
    val genres: List<String>? = null,

    @SerializedName("images")
    val images: List<String>? = null
)

    fun MovieDto.toMovie(): Movie {
        return Movie(
            id = id,
            title = title,
            poster = poster,
            year = year,
            country = country,
            rating = rating,
            genres = genres,
            images = images
        )
    }

    fun Movie.toMovieDto(): MovieDto {
        return MovieDto(
            id = id,
            title = title,
            poster = poster,
            year = year,
            country = country,
            rating = rating,
            genres = genres,
            images = images
        )
    }

    fun MovieDto.toMovieEntity(): MovieEntity {
        return MovieEntity(
            id = id,
            title = title,
            poster = poster,
            year = year,
            country = country
        )
    }








