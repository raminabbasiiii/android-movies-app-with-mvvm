package com.raminabbasiiii.movies.ui.movie.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.raminabbasiiii.movies.*
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.raminabbasiiii.movies.model.Movie
import com.raminabbasiiii.movies.AndroidMainCoroutinesRule
import com.raminabbasiiii.movies.ui.movie.details.MovieDetailsFragment
import kotlinx.coroutines.test.runTest
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@RunWith(AndroidJUnit4ClassRunner::class)
class MovieListFragmentTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var coroutinesRule = AndroidMainCoroutinesRule()

    private val data = listOf(
        Movie(1,"The Shawshank Redemption","http://moviesapi.ir/images/tt0111161_poster.jpg",
            "1994", "USA","9.3",listOf("Crime","Drama"),
            listOf("http://moviesapi.ir/images/tt0111161_screenshot1.jpg")),
        Movie(2,"title2","poster","year",
            "country","rating",null,null),
        Movie(3,"title3","poster","year",
            "country","rating",null,null),
        Movie(4,"title4","poster","year",
            "country","rating",null,null))

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun isMovieListVisible() {
        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<MovieListFragment> {
            Navigation.setViewNavController(requireView(), navController)

            runTest {
                recyclerAdapter.submitData(PagingData.from(data))
            }
        }

        onView(withId(R.id.movie_recycler_view)).check(matches(isDisplayed()))
    }

    @Test
    fun clickMovieListItem_navigateToMovieDetailsFragment() {
        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<MovieListFragment> {
            Navigation.setViewNavController(requireView(),navController)

            runTest {
                recyclerAdapter.submitData(PagingData.from(data))
            }
        }

        onView(withId(R.id.movie_recycler_view))
            .perform(
                actionOnItemAtPosition<MovieRecyclerAdapter.MovieViewHolder>(0, click())
            )

        verify(navController).navigate(
            MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment(data[0].id)
        )

    }

}




















