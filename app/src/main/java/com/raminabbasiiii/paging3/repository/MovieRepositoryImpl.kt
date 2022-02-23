package com.raminabbasiiii.paging3.repository

import androidx.paging.*
import com.raminabbasiiii.paging3.data.db.AppDatabase
import com.raminabbasiiii.paging3.data.db.movie.MovieEntity
import com.raminabbasiiii.paging3.data.db.moviedetails.MovieDetailsEntity
import com.raminabbasiiii.paging3.data.network.Api
import com.raminabbasiiii.paging3.data.network.toMovieDetailsEntity
import com.raminabbasiiii.paging3.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class MovieRepositoryImpl
@Inject
constructor(
    private val api: Api,
    private val database: AppDatabase
    ): MovieRepository {

    private val pagingSourceFactory = { database.movieDao().getAllMovie() }

    @OptIn(ExperimentalPagingApi::class)
    override fun getAllMovies(): Flow<PagingData<MovieEntity>> {
        return Pager(
            config = PagingConfig(1),
            remoteMediator = MovieRemoteMediator (api, database),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override fun getMovieDetails(
        movieId: Int
    ): Flow<Resource<MovieDetailsEntity>> = flow{
        try {
            emit(Resource.loading(null))
            if (database.movieDetailsDao().isExist(movieId)) {
                emit(Resource.success(database.movieDetailsDao().getMovieDetails(movieId)))
            } else {
                val response = api.getMovieDetails(movieId).toMovieDetailsEntity()
                database.movieDetailsDao().insertMovieDetails(response)
                emit(Resource.success(database.movieDetailsDao().getMovieDetails(movieId)))
            }
        }catch (e : Exception) {
            emit(Resource.error("Check Network Connection!",null))
        }
    }
}