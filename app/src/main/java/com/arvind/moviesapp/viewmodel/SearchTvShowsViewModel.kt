package com.arvind.moviesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arvind.moviesapp.repository.SearchTvShowsRepository
import com.arvind.moviesapp.response.popular.ResponseTv_shows
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchTvShowsViewModel @Inject constructor(private val searchTvShowsRepository: SearchTvShowsRepository) :
    ViewModel() {

    fun getListSearchData(): Flow<PagingData<ResponseTv_shows>> {
        return searchTvShowsRepository.getsearchtvshows().cachedIn(viewModelScope)
    }
}