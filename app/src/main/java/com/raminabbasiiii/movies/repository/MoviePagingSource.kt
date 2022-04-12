package com.raminabbasiiii.movies.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.PagingSource.LoadResult.Page
import com.raminabbasiiii.movies.data.network.Api
import com.raminabbasiiii.movies.data.network.toMovie
import com.raminabbasiiii.movies.model.Movie
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MoviePagingSource
@Inject
constructor(
    private val api: Api
    ) : PagingSource<Int, Movie>(){

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let {
            //state.closestPageToPosition(it)?.prevKey
                anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            //val page = if (params is Append) params.key else null
            val page = params.key ?: 1

            val response = api.getAllMovies(page).data.map { it.toMovie() }
            Page (
                data = response,
                prevKey = null,
                nextKey = page + 1
            )
        } catch (e : IOException) {
            LoadResult.Error(e)
        } catch (e : HttpException) {
            LoadResult.Error(e)
        }
    }


}