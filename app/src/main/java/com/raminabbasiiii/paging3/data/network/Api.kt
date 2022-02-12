package com.raminabbasiiii.paging3.data.network

import com.raminabbasiiii.paging3.data.network.responses.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("movies")
    suspend fun getAllMovies(
        @Query("page") page: Int? = null
    ): MovieListResponse

    @GET("movies")
    suspend fun searchMovies(
        @Query("q") name: String
    ): MovieListResponse
}