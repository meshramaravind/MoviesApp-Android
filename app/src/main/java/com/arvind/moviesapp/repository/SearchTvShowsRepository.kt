package com.arvind.moviesapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.arvind.moviesapp.paging.SearchTvShowsPagingDataSource
import com.arvind.moviesapp.response.popular.ResponseTv_shows
import com.arvind.moviesapp.webapi.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchTvShowsRepository @Inject constructor(
    private val apiService: ApiService,
    private val query: String
) {
    fun getsearchtvshows(): Flow<PagingData<ResponseTv_shows>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 10),
            pagingSourceFactory = {
                SearchTvShowsPagingDataSource(apiService, query)
            }
        ).flow
    }
}