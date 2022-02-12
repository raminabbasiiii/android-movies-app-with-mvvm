package com.raminabbasiiii.paging3.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.raminabbasiiii.paging3.repository.inMemory.InMemoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val repository: InMemoryRepository
    ): ViewModel(){

    @ExperimentalPagingApi
    val movies = repository.getAllMovies().cachedIn(viewModelScope)

    /*@ExperimentalPagingApi
    fun observeMovies(): Flow<PagingData<Movie>> {
        return repository
            .getAllMovie()
            .cachedIn(viewModelScope)
    }*/
}