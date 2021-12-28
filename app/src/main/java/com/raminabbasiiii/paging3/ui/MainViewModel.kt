package com.raminabbasiiii.paging3.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.raminabbasiiii.paging3.datasource.db.entity.Movie
import com.raminabbasiiii.paging3.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val repository: MainRepository
    ): ViewModel(){

    @ExperimentalPagingApi
    val movies = repository.getAllMovie().cachedIn(viewModelScope)

    /*@ExperimentalPagingApi
    fun observeMovies(): Flow<PagingData<Movie>> {
        return repository
            .getAllMovie()
            .cachedIn(viewModelScope)
    }*/
}