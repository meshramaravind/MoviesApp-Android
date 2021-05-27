package com.arvind.moviesapp.model.showdetails

import com.arvind.moviesapp.response.showdetails.ResponseTvShowDetails
import com.google.gson.annotations.SerializedName

data class DataModelShowDetailsBase(@SerializedName("tvShow") val tvShow : ResponseTvShowDetails)
