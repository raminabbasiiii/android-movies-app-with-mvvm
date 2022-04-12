package com.raminabbasiiii.movies.repository

import androidx.paging.PagingData
import com.raminabbasiiii.movies.model.Movie
import com.raminabbasiiii.movies.model.MovieDetails
import com.raminabbasiiii.movies.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getAllMovies(): Flow<PagingData<Movie>>
    fun getMovieDetails(movieId: Int): Flow<Resource<MovieDetails>>
}