package com.arvind.moviesapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.arvind.moviesapp.repository.TvShowsDetailsRepository
import com.arvind.moviesapp.response.showdetails.ResponseTvShowDetails
import com.arvind.moviesapp.webapi.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvShowsDetailsViewModel @Inject constructor(
    private val apiService: ApiService, application: Application, private val id: String
) :
    AndroidViewModel(application) {
    private val tvShowsDetailsRepository: TvShowsDetailsRepository

    val allTvShowDetails: LiveData<List<ResponseTvShowDetails>>
        get() = tvShowsDetailsRepository.getMutableLiveData()

    init {
        tvShowsDetailsRepository = TvShowsDetailsRepository(apiService, application, id)
    }
}