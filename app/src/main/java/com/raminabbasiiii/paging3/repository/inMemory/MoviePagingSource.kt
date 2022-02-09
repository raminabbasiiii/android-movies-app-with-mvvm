package com.raminabbasiiii.paging3.repository.inMemory

import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.PagingSource.LoadParams.Append
import androidx.paging.PagingSource.LoadResult.Page
import com.raminabbasiiii.paging3.datasource.db.entity.Movie
import com.raminabbasiiii.paging3.datasource.network.Api
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MoviePagingSource
@Inject
constructor(
    private val api: Api
    ) : PagingSource<Int,Movie>(){

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

            val data = api.getAllMovies(page)

            Page (
                data = data.movies,
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