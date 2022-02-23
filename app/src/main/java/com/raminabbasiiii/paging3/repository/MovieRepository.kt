package com.raminabbasiiii.paging3.repository

import androidx.paging.PagingData
import com.raminabbasiiii.paging3.data.db.movie.MovieEntity
import com.raminabbasiiii.paging3.data.db.moviedetails.MovieDetailsEntity
import kotlinx.coroutines.flow.Flow
import com.raminabbasiiii.paging3.util.Resource

interface MovieRepository {

    fun getAllMovies(): Flow<PagingData<MovieEntity>>
    fun getMovieDetails(movieId: Int): Flow<Resource<MovieDetailsEntity>>
}