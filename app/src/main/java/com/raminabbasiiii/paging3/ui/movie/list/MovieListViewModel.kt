package com.raminabbasiiii.paging3.ui.movie.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.raminabbasiiii.paging3.repository.InMemoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel
@Inject
constructor(
    private val repository: InMemoryRepository
    ): ViewModel(){

    @ExperimentalPagingApi
    val movies = repository.getAllMovies().cachedIn(viewModelScope)
}