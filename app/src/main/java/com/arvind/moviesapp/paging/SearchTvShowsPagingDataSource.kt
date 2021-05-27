package com.arvind.moviesapp.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.arvind.moviesapp.response.popular.ResponseTv_shows
import com.arvind.moviesapp.webapi.ApiService
import retrofit2.HttpException
import java.io.IOException


class SearchTvShowsPagingDataSource(
    private val apiService: ApiService, private val query: String
) : PagingSource<Int, ResponseTv_shows>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResponseTv_shows> {
        return try {
            val nextPageNumber = params.key ?: 0
            val response = apiService.getsearchTVShow(
                query,
                nextPageNumber
            )
            val responseData = mutableListOf<ResponseTv_shows>()
            val data = response.body()?.tv_shows ?: emptyList()
            responseData.addAll(data)

            LoadResult.Page(
                data = responseData,
                prevKey = if (nextPageNumber > 0) nextPageNumber - 1 else null,
                nextKey = if (nextPageNumber < response.body()?.pages!!) nextPageNumber + 1 else null
            )
        } catch (exception: IOException) {
            val error = IOException("Please Check Internet Connection")
            LoadResult.Error(error)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ResponseTv_shows>): Int? {
        return null
    }
}