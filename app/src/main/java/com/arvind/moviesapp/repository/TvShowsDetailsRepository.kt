package com.arvind.moviesapp.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.arvind.moviesapp.model.showdetails.DataModelShowDetailsBase
import com.arvind.moviesapp.response.showdetails.ResponseTvShowDetails
import com.arvind.moviesapp.webapi.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList
import javax.inject.Inject


class TvShowsDetailsRepository @Inject constructor(
    private val apiService: ApiService, application: Application, private val id: String
) {

    private var tvShowsArrayList = ArrayList<ResponseTvShowDetails>()
    private val mutableLiveData = MutableLiveData<List<ResponseTvShowDetails>>()

    fun getMutableLiveData(): MutableLiveData<List<ResponseTvShowDetails>> {
        val userDataService = apiService
        val call = userDataService.getTVShowDetails(id)

        call.enqueue(object : Callback<DataModelShowDetailsBase> {
            internal var message: String? = null

            override fun onResponse(
                call: Call<DataModelShowDetailsBase>,
                response: Response<DataModelShowDetailsBase>
            ) {

                if (response.isSuccessful()) {
                    val apiresponse: DataModelShowDetailsBase? = response.body()
                    if (apiresponse != null) {
                        tvShowsArrayList =
                            apiresponse.tvShow as ArrayList<ResponseTvShowDetails>
                        mutableLiveData.setValue(tvShowsArrayList)
                    }
                }

            }

            override fun onFailure(call: Call<DataModelShowDetailsBase>, t: Throwable) {
                t.printStackTrace()
                Log.d(TAG, t.message!!)

            }
        })

        return mutableLiveData
    }

    companion object {

        private val TAG = "TvShowDetailsRepo"
    }
}