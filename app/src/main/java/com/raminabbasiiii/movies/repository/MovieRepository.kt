package com.raminabbasiiii.movies.repository

import androidx.paging.PagingData
import com.raminabbasiiii.movies.data.db.movie.MovieEntity
import com.raminabbasiiii.movies.data.db.moviedetails.MovieDetailsEntity
import kotlinx.coroutines.flow.Flow
import com.raminabbasiiii.movies.util.Resource

interface MovieRepository {

    fun getAllMovies(): Flow<PagingData<MovieEntity>>
    fun getMovieDetails(movieId: Int): Flow<Resource<MovieDetailsEntity>>
}