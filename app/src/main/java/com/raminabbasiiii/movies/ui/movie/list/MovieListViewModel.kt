package com.raminabbasiiii.movies.ui.movie.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.raminabbasiiii.movies.data.db.movie.MovieEntity
import com.raminabbasiiii.movies.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MovieListViewModel
@Inject
constructor(
    private val repository: MovieRepository
    ): ViewModel(){

    private val _movies : MutableLiveData<PagingData<MovieEntity>> = MutableLiveData()
    val movies : LiveData<PagingData<MovieEntity>> = _movies

    init {
        observeAllMovies()
    }

    private fun observeAllMovies() {
        viewModelScope.launch {
            repository
                .getAllMovies()
                .cachedIn(viewModelScope)
                .collect { _movies.value = it }
        }
    }
}