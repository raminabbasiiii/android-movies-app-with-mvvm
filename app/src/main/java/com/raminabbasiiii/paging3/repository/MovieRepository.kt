package com.raminabbasiiii.paging3.repository

import androidx.paging.PagingData
import com.raminabbasiiii.paging3.model.Movie
import com.raminabbasiiii.paging3.model.MovieDetails
import com.raminabbasiiii.paging3.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getAllMovies(): Flow<PagingData<Movie>>
    //fun getMoviesByPage(page : Int): Flow<PagingData<Movie>>
    fun getMovieDetails(movieId: Int): Flow<Resource<MovieDetails>>

}