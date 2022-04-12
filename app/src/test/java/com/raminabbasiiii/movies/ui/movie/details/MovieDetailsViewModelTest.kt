package com.raminabbasiiii.movies.ui.movie.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.raminabbasiiii.movies.MainCoroutinesRule
import com.raminabbasiiii.movies.getOrAwaitValueTest
import com.raminabbasiiii.movies.model.MovieDetails
import com.raminabbasiiii.movies.repository.MovieRepository
import com.raminabbasiiii.movies.util.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.`when` as whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieDetailsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutinesRule = MainCoroutinesRule()

    private lateinit var viewModel: MovieDetailsViewModel

    @Mock
    lateinit var movieDetails: MovieDetails

    @Mock
    lateinit var repository: MovieRepository

    @Mock
    lateinit var movieDetailsResponseObserver: Observer<Resource<MovieDetails>>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = MovieDetailsViewModel(repository)
    }

    @Test
    fun `when fetching results ok then return a movie details successfully`() {
        runTest {
            val flow = flow {
                emit(Resource.loading(null))
                emit(Resource.success(movieDetails))
            }
            val res = Resource.success(movieDetails)
            viewModel.movieDetails.observeForever(movieDetailsResponseObserver)
            given(repository.getMovieDetails(anyInt())).willReturn(flow)
            //verify(repository).getMovieDetails(anyInt())
            viewModel.setMovieId(anyInt())
            assertNotNull(viewModel.movieDetails.value)
            assertEquals(res,viewModel.movieDetails.getOrAwaitValueTest())
        }
    }

    @Test
    fun `when fetching results fails then return error`() {

        runTest {
            val flow = flow {
                emit(Resource.error("Check Network Connection!",null))
            }
            viewModel.movieDetails.observeForever(movieDetailsResponseObserver)
            whenever(repository.getMovieDetails(anyInt())).thenAnswer {
                flow
            }
            viewModel.setMovieId(anyInt())
            assertNotNull(viewModel.movieDetails.value)
            assertEquals(Resource.error("Check Network Connection!",null),
                viewModel.movieDetails.getOrAwaitValueTest())
        }
    }
}



















