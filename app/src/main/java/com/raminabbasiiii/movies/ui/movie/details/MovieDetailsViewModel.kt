package com.raminabbasiiii.movies.ui.movie.details

import androidx.lifecycle.*
import com.raminabbasiiii.movies.model.MovieDetails
import com.raminabbasiiii.movies.repository.MovieRepository
import com.raminabbasiiii.movies.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel
@Inject
constructor(
    private val repository: MovieRepository
): ViewModel() {

    private val _movieDetails : MutableLiveData<Resource<MovieDetails>> = MutableLiveData()
    val movieDetails : LiveData<Resource<MovieDetails>> = _movieDetails

    /*init {
        getMovieDetails(movieId)
    }*/

    fun setMovieId(movieId: Int) {
        getMovieDetails(movieId)
    }

    private fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            repository
                .getMovieDetails(movieId)
                .collect {
                    _movieDetails.value = it
                }
        }
    }
}