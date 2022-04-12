package com.raminabbasiiii.movies.ui.movie.list

sealed class MovieListEvent {

    object RestoreStateEvent : MovieListEvent()
    object GetMovieListEvent : MovieListEvent()
}
