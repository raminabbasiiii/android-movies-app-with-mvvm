package com.raminabbasiiii.paging3.datasource.network

import com.raminabbasiiii.paging3.datasource.network.response.Movies
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("movies")
    suspend fun getAllMovies(
        @Query("page") page: Int?
    ): Movies
}