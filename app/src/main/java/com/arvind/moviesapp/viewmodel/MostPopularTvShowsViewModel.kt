package com.arvind.moviesapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arvind.moviesapp.repository.MostPopularTVShowsRepository
import com.arvind.moviesapp.response.popular.ResponseTv_shows
import com.arvind.moviesapp.storage.UIModeDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MostPopularTvShowsViewModel @Inject constructor(private val mostPopularTVShowsRepository: MostPopularTVShowsRepository,private val application: Application) :
    ViewModel() {

    fun getListData(): Flow<PagingData<ResponseTv_shows>> {
        return mostPopularTVShowsRepository.getmostpopularshows().cachedIn(viewModelScope)
    }
    private val uiModeDataStore = UIModeDataStore(application)

    // get ui mode
    val getUIMode = uiModeDataStore.uiMode

    // save ui mode
    fun saveToDataStore(isNightMode: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            uiModeDataStore.saveToDataStore(isNightMode)
        }
    }

}