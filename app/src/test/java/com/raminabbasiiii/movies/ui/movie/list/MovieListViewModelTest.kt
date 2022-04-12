package com.raminabbasiiii.movies.ui.movie.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import com.raminabbasiiii.movies.MainCoroutinesRule
import com.raminabbasiiii.movies.getOrAwaitValueTest
import com.raminabbasiiii.movies.model.Movie
import com.raminabbasiiii.movies.repository.MovieRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieListViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutinesRule = MainCoroutinesRule()




    private lateinit var viewModel: MovieListViewModel

    private val _res : MutableLiveData<PagingData<Movie>> = MutableLiveData()
    val res : LiveData<PagingData<Movie>> = _res

    @Mock
    lateinit var repository: MovieRepository

    @Mock
    lateinit var movieListResponseObserver: Observer<PagingData<Movie>>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = MovieListViewModel(repository)
    }

    // first remove cachedIn(viewModelScope) in getAllMovies fun in MovieListViewModel then run this test
    @Test
    fun `when return a movies list successfully`() {
        runTest {
            val flow = flow<PagingData<Movie>> {
                emit(PagingData.empty())
            }
            flow.collect {
                _res.value = it
            }
            viewModel.movies.observeForever(movieListResponseObserver)
            given(repository.getAllMovies()).willReturn(flow)
            verify(repository).getAllMovies()
            viewModel.getAllMovies()
            assertNotNull(viewModel.movies.value)
            assertEquals(res.value,viewModel.movies.getOrAwaitValueTest())
        }
    }

}

















