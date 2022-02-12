package com.raminabbasiiii.paging3.repository.inMemory

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.raminabbasiiii.paging3.data.network.Api
import com.raminabbasiiii.paging3.model.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InMemoryRepository
@Inject
constructor(
    private val api: Api
    ) {

    fun getAllMovies(): Flow<PagingData<Movie>> = Pager(
        PagingConfig(25)
    ) {
        MoviePagingSource(api)
    }.flow
}