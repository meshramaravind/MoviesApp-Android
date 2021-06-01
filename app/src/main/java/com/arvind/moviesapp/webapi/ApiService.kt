package com.arvind.moviesapp.webapi

import com.arvind.moviesapp.model.popular.DataModelPapularBase
import com.arvind.moviesapp.model.showdetails.DataModelShowDetailsBase
import com.arvind.moviesapp.response.showdetails.ResponseTvShowDetails
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("most-popular")
    suspend fun getMostPopularTVShows(@Query("page") page: Int): Response<DataModelPapularBase>

    @GET("show-details")
    fun getTVShowDetails(@Query("q") tvShowId: String?): Call<DataModelShowDetailsBase>

    @GET("search")
    suspend fun getsearchTVShow(
        @Query("q") query: String?,
        @Query("page") page: Int
    ): Response<DataModelPapularBase>
}