package com.raminabbasiiii.paging3.ui.movie.list

sealed class MovieListEvent {

    object RestoreStateEvent : MovieListEvent()
    object GetMovieListEvent : MovieListEvent()
}
