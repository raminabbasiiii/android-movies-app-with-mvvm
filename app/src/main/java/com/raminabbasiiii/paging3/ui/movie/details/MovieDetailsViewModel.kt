package com.raminabbasiiii.paging3.ui.movie.details

import androidx.lifecycle.*
import androidx.paging.RemoteMediator
import androidx.paging.cachedIn
import com.raminabbasiiii.paging3.model.Movie
import com.raminabbasiiii.paging3.model.MovieDetails
import com.raminabbasiiii.paging3.repository.MovieRepository
import com.raminabbasiiii.paging3.util.Resource
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

    fun setMovieId(movieId: Int) {
        observeMovieDetails(movieId)
    }

    private fun observeMovieDetails(movieId: Int) {
        viewModelScope.launch {
            repository
                .getMovieDetails(movieId)
                .collect {
                    _movieDetails.value = it
                }
        }
    }
}