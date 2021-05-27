package com.arvind.moviesapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.arvind.moviesapp.paging.MostPapularPagingDataSource
import com.arvind.moviesapp.response.popular.ResponseTv_shows
import com.arvind.moviesapp.webapi.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MostPopularTVShowsRepository @Inject constructor(
    private val apiService: ApiService,
) {
    fun getmostpopularshows(): Flow<PagingData<ResponseTv_shows>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 10),
            pagingSourceFactory = {
                MostPapularPagingDataSource(apiService)
            }
        ).flow
    }


}