package com.raminabbasiiii.paging3.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.raminabbasiiii.paging3.data.network.Api
import com.raminabbasiiii.paging3.data.network.toMovie
import com.raminabbasiiii.paging3.data.network.toMovieDetails
import com.raminabbasiiii.paging3.model.Movie
import com.raminabbasiiii.paging3.model.MovieDetails
import com.raminabbasiiii.paging3.util.Resource
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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