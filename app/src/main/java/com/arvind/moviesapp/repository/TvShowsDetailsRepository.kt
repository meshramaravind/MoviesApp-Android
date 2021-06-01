package com.arvind.moviesapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arvind.moviesapp.model.showdetails.DataModelShowDetailsBase
import com.arvind.moviesapp.webapi.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.inject.Inject


class TvShowsDetailsRepository @Inject constructor(
    private val apiService: ApiService
) {

    fun getTVShowDetails(id: String?): LiveData<DataModelShowDetailsBase?>? {
        val data: MutableLiveData<DataModelShowDetailsBase?> =
            MutableLiveData<DataModelShowDetailsBase?>()
        apiService.getTVShowDetails(id).enqueue(object : Callback<DataModelShowDetailsBase?> {
            override fun onResponse(
                call: Call<DataModelShowDetailsBase?>,
                response: Response<DataModelShowDetailsBase?>
            ) {
                data.setValue(response.body())
            }

            override fun onFailure(call: Call<DataModelShowDetailsBase?>, t: Throwable) {
                data.setValue(null)
            }
        })
        return data
    }

    companion object {

        private val TAG = "TvShowDetailsRepo"
    }
}