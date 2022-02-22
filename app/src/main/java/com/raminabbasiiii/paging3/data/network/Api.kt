package com.raminabbasiiii.paging3.data.network

import com.raminabbasiiii.paging3.data.network.responses.MovieListResponse
import retrofit2.http.*

interface Api {

    @GET("movies")
    suspend fun getAllMovies(
        @Query("page") page: Int? = null
    ): MovieListResponse

    @GET("movies/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int
    ): MovieDto

    @GET("movies")
    suspend fun searchMovies(
        @Query("q") name: String
    ): MovieListResponse
}