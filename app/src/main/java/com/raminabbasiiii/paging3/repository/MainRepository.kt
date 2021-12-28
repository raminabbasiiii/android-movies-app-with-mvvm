package com.raminabbasiiii.paging3.repository

import androidx.paging.*
import com.raminabbasiiii.paging3.datasource.db.AppDatabase
import com.raminabbasiiii.paging3.datasource.db.entity.Movie
import com.raminabbasiiii.paging3.datasource.network.Api
import com.raminabbasiiii.paging3.repository.remotemediator.MovieRemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainRepository
@Inject
constructor(
    private val api: Api,
    private val database: AppDatabase
    ) {

    private val pagingSourceFactory = { database.movieDao().getAllMovie() }

    @ExperimentalPagingApi
    fun getAllMovie(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(25),
            remoteMediator = MovieRemoteMediator (api, database),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}