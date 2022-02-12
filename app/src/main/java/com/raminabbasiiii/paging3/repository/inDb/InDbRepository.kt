package com.raminabbasiiii.paging3.repository.inDb

import androidx.paging.*
import com.raminabbasiiii.paging3.data.db.AppDatabase
import com.raminabbasiiii.paging3.data.db.movie.MovieEntity
import com.raminabbasiiii.paging3.data.network.Api
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InDbRepository
@Inject
constructor(
    private val api: Api,
    private val database: AppDatabase
    ) {

    private val pagingSourceFactory = { database.movieDao().getAllMovie() }

    @ExperimentalPagingApi
    fun getAllMovies(): Flow<PagingData<MovieEntity>> {
        return Pager(
            config = PagingConfig(25),
            remoteMediator = MovieRemoteMediator (api, database),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}