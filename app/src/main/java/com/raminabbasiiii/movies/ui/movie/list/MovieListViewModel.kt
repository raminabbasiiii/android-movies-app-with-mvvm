package com.raminabbasiiii.movies.ui.movie.list

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.raminabbasiiii.movies.model.Movie
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

    private val _movies : MutableLiveData<PagingData<Movie>> = MutableLiveData()
    val movies : LiveData<PagingData<Movie>> = _movies

    init {
        getAllMovies()
    }

    fun getAllMovies() {
        viewModelScope.launch {
            repository
                .getAllMovies()
                .cachedIn(viewModelScope)
                .collect { _movies.value = it  }
        }
    }

}