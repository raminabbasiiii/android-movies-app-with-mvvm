package com.raminabbasiiii.paging3.repository.inMemory

import androidx.paging.PagingSource.LoadParams.Refresh
import androidx.paging.PagingSource.LoadResult.Page
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.raminabbasiiii.paging3.MainCoroutinesRule
import com.raminabbasiiii.paging3.datasource.db.entity.Movie
import com.raminabbasiiii.paging3.datasource.network.Api
import com.raminabbasiiii.paging3.datasource.network.response.Movies
import com.raminabbasiiii.paging3.model.Meta
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.`when`
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
        val apiResponse = Movies(
            movies = listOf(
                Movie(id = 1, title = "title", poster = "poster", year = "year", country = "country"),
                Movie(id = 2, title = "title", poster = "poster", year = "year", country = "country"),
                Movie(id = 3, title = "title", poster = "poster", year = "year", country = "country")
            ),
            meta = Meta(currentPage = "1", perPage = 10, pageCount = 25, totalCount = 100 )
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
            data = apiResponse.movies.map {
                Movie(it.id,it.title,it.poster,it.year,it.country)},
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