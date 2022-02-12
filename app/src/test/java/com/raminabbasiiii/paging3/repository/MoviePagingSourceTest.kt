package com.raminabbasiiii.paging3.repository

import androidx.paging.PagingSource.LoadParams.Refresh
import androidx.paging.PagingSource.LoadResult.Page
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.raminabbasiiii.paging3.MainCoroutinesRule
import com.raminabbasiiii.paging3.data.network.Api
import com.raminabbasiiii.paging3.data.network.MovieDto
import com.raminabbasiiii.paging3.data.network.responses.MovieListResponse
import com.raminabbasiiii.paging3.data.network.toMovie
import com.raminabbasiiii.paging3.model.MetaData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class MoviePagingSourceTest {

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
                    id = 1, title = "title", poster = "poster", year = "year", country = "country",
                    rating = "rating", genres = listOf("one","two","three"),
                    images = listOf("one","two","three")),
                MovieDto(
                    id = 2, title = "title", poster = "poster", year = "year", country = "country",
                    rating = "rating", genres = listOf("one","two","three"),
                    images = listOf("one","two","three")),
                MovieDto(
                    id = 3, title = "title", poster = "poster", year = "year", country = "country",
                    rating = "rating", genres = listOf("one","two","three"),
                    images = listOf("one","two","three"))
            ),
            metaData = MetaData(currentPage = "1", perPage = 10, pageCount = 25, totalCount = 100 )
        )
    }

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        moviePagingSource = MoviePagingSource(api)
    }

    @Test
    fun `movies paging source refresh - success`() = runBlockingTest {
        given(api.getAllMovies(any())).willReturn(apiResponse)
        //`when`(api.getAllMovies(any())).thenReturn(apiResponse)
        val expectedResult = Page(
            data = apiResponse.data.map { it.toMovie()
                //MovieDto(it.id,it.title,it.poster,it.year,it.country,it.rating,it.genres,it.images)
                                        },
            prevKey = null,
            nextKey = 2
        )
        assertEquals(
            expectedResult, moviePagingSource.load(
                Refresh(
                    key = null,
                    loadSize = 4,
                    placeholdersEnabled = false
                )
            )
        )
    }


}