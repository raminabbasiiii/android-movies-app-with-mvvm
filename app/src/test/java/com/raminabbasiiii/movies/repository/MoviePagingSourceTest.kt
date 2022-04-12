package com.raminabbasiiii.movies.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import com.raminabbasiiii.movies.MainCoroutinesRule
import com.raminabbasiiii.movies.data.network.Api
import com.raminabbasiiii.movies.data.network.MovieDto
import com.raminabbasiiii.movies.data.network.responses.MovieListResponse
import com.raminabbasiiii.movies.data.network.toMovie
import com.raminabbasiiii.movies.model.MetaData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class MoviePagingSourceTest{

    @get: Rule
    var coroutinesRule = MainCoroutinesRule()

    @get: Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var api: Api

    private lateinit var moviePagingSource: MoviePagingSource

    companion object {
        val apiResponse = MovieListResponse(
            data = listOf(
                MovieDto(
                    1,"title","poster","year","country","rating",
                    "rated","released","runtime","director","writer",
                    "actors","plot","awards","votes",null,null),
                MovieDto(
                    2,"title","poster","year","country","rating",
                    "rated","released","runtime","director","writer",
                    "actors","plot","awards","votes",null,null),
                MovieDto(
                    3,"title","poster","year","country","rating",
                    "rated","released","runtime","director","writer",
                    "actors","plot","awards","votes",null,null)
            ),
            metaData = MetaData("1",10,1,1 )
        )
    }

    @Before
    fun setup(){
        MockitoAnnotations.openMocks(this)
        moviePagingSource = MoviePagingSource(api)
    }

    @Test
    fun `movies paging source refresh - success`() = runBlockingTest {
        BDDMockito.given(api.getAllMovies(ArgumentMatchers.any())).willReturn(apiResponse)
        //`when`(api.getAllMovies(any())).thenReturn(apiResponse)
        val expectedResult = PagingSource.LoadResult.Page(
            data = apiResponse.data.map { it.toMovie()
                //MovieDto(it.id,it.title,it.poster,it.year,it.country,it.rating,it.genres,it.images)
            },
            prevKey = null,
            nextKey = 2
        )
        kotlin.test.assertEquals(
            expectedResult, moviePagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 4,
                    placeholdersEnabled = false
                )
            )
        )
    }
}