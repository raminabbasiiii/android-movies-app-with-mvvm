package com.raminabbasiiii.paging3.repository.inDb

import androidx.paging.*
import com.raminabbasiiii.paging3.datasource.db.AppDatabase
import com.raminabbasiiii.paging3.datasource.db.entity.Movie
import com.raminabbasiiii.paging3.datasource.network.Api
import com.raminabbasiiii.paging3.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InDbRepository
@Inject
constructor(
    private val api: Api,
    private val database: AppDatabase
    ) : MainRepository {

    private val pagingSourceFactory = { database.movieDao().getAllMovie() }

    @ExperimentalPagingApi
    override fun getAllMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(25),
            remoteMediator = MovieRemoteMediator (api, database),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}