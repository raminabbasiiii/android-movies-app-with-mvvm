package com.raminabbasiiii.paging3.repository

import androidx.paging.PagingData
import com.raminabbasiiii.paging3.datasource.db.entity.Movie
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    fun getAllMovies() : Flow<PagingData<Movie>>
}