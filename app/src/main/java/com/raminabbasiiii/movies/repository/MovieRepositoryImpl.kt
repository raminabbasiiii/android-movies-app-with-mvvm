package com.raminabbasiiii.movies.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.raminabbasiiii.movies.data.network.Api
import com.raminabbasiiii.movies.data.network.toMovieDetails
import com.raminabbasiiii.movies.model.Movie
import com.raminabbasiiii.movies.model.MovieDetails
import com.raminabbasiiii.movies.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class MovieRepositoryImpl
@Inject
constructor(
    private val api: Api
    ): MovieRepository{

    override fun getAllMovies(): Flow<PagingData<Movie>> = Pager(
        PagingConfig(1)
    ) {
        MoviePagingSource(api)
    }.flow

    /*override fun getMoviesByPage(page: Int): Flow<PagingData<Movie>> = Pager(
        PagingConfig(page)
    ) {
        MoviePagingSource(api)
    }.flow*/

    override fun getMovieDetails(movieId: Int): Flow<Resource<MovieDetails>> = flow {
        try {
            emit(Resource.loading(null))
            emit(Resource.success(api.getMovieDetails(movieId).toMovieDetails()))

        } catch (e: Exception) {
            emit(Resource.error("Check Network Connection!",null))
        }
    }

}














