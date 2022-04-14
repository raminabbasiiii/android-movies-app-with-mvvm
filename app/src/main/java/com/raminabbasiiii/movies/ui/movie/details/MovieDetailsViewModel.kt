package com.raminabbasiiii.movies.ui.movie.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raminabbasiiii.movies.data.db.moviedetails.MovieDetailsEntity
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

        private val _movieDetails : MutableLiveData<Resource<MovieDetailsEntity>> = MutableLiveData()
        val movieDetails : LiveData<Resource<MovieDetailsEntity>> = _movieDetails

        fun setMovieId(movieId: Int) {
            observeMovieDetails(movieId)
        }

        private fun observeMovieDetails(movieId: Int) {
            viewModelScope.launch {
                repository
                    .getMovieDetails(movieId)
                    .collect { _movieDetails.value = it }
            }
        }
    }