package com.raminabbasiiii.paging3.ui.movie.list

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.raminabbasiiii.paging3.model.Movie
import com.raminabbasiiii.paging3.repository.MovieRepository
import com.raminabbasiiii.paging3.util.Constants.Companion.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

//const val STATE_KEY_PAGE = "movie.state.page.key"
//const val STATE_KEY_LIST_POSITION = "movie.state.list.list_position"

@ExperimentalCoroutinesApi
@HiltViewModel
class MovieListViewModel
@Inject
constructor(
    private val repository: MovieRepository,
    private val savedStateHandle: SavedStateHandle
    ): ViewModel(){

    private val _movies : MutableLiveData<PagingData<Movie>> = MutableLiveData()
    val movies : LiveData<PagingData<Movie>> = _movies

    init {
        observeAllMovies()
    }

    private fun observeAllMovies() {
        viewModelScope.launch {
            repository
                .getAllMovies()
                .cachedIn(viewModelScope)
                .collect { _movies.value = it  }
        }
    }

    /*var movieListScrollPosition = 0
    var pageNumber = 1

    init {
        savedStateHandle.get<Int>(STATE_KEY_PAGE)?.let { p ->
            setPage(p)
        }
        savedStateHandle.get<Int>(STATE_KEY_LIST_POSITION)?.let { p ->
            setListScrollPosition(p)
        }

        if(movieListScrollPosition != 0){
            onTriggerEvent(MovieListEvent.RestoreStateEvent)
        } else {
            onTriggerEvent(MovieListEvent.GetMovieListEvent)
        }

        getAllMovies()

    }

    fun onTriggerEvent(event: MovieListEvent) {
        viewModelScope.launch {
            try {
                when(event) {
                    is MovieListEvent.RestoreStateEvent -> {
                        restoreState()
                        Log.d(TAG, "launchJob: restoreState called.")
                    }
                    is MovieListEvent.GetMovieListEvent -> {
                        getAllMovies()
                        Log.d(TAG, "launchJob: getAllMovies+ called.")
                    }
                }

            }catch (e: Exception) {
                Log.e(TAG, "launchJob: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            }
            finally {
                Log.d(TAG, "launchJob: finally called.")
            }
        }

    }

    private fun restoreState() {
        /*for (p in 1 .. pageNumber) {
            val result = repository
                .getMoviesByPage(p).cachedIn(viewModelScope)

            if(p == pageNumber){ // done
                _movies.value = result.asLiveData().value
            }
        }*/
        viewModelScope.launch {
            repository
                .getMoviesByPage(pageNumber)
                .cachedIn(viewModelScope)
                .collect { _movies.value = it  }
        }
    }

    private fun getAllMovies() {
        viewModelScope.launch {
            repository
                .getAllMovies()
                .cachedIn(viewModelScope)
                .collect { _movies.value = it  }
        }
    }

    fun onChangeMovieScrollPosition(position: Int){
        setListScrollPosition(position = position)
    }

    fun setPage(page: Int){
        this.pageNumber = page
        savedStateHandle[STATE_KEY_PAGE] = page
    }

    private fun setListScrollPosition(position: Int){
        movieListScrollPosition = position
        savedStateHandle[STATE_KEY_LIST_POSITION] = position
    }


    //val movies = repository.getAllMovies().cachedIn(viewModelScope)
        val movies : LiveData<PagingData<Movie>> = savedStateHandle
        .getLiveData<PagingData<Movie>>("movies").switchMap {
            repository.getAllMovies().asLiveData()
        }.cachedIn(viewModelScope)

    init {
        setMovies()
    }

    fun setMovies() {
        savedStateHandle["movies"] = movies.value
    }

    val movies : LiveData<PagingData<Movie>> = repository
        .getAllMovies()
        .asLiveData()
        .cachedIn(viewModelScope)*/

}