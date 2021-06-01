package com.arvind.moviesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.arvind.moviesapp.model.showdetails.DataModelShowDetailsBase
import com.arvind.moviesapp.repository.TvShowsDetailsRepository
import com.arvind.moviesapp.response.showdetails.ResponseTvShowDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvShowsDetailsViewModel @Inject constructor(
    private val repository: TvShowsDetailsRepository
) : ViewModel() {
    fun getTVShowDetails(id: String): LiveData<DataModelShowDetailsBase?>? {
        return repository.getTVShowDetails(id)
    }
}



