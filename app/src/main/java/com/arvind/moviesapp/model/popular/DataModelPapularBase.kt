package com.arvind.moviesapp.model.popular

import com.arvind.moviesapp.response.popular.ResponseTv_shows
import com.google.gson.annotations.SerializedName

data class DataModelPapularBase(
    @SerializedName("total") val total: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("pages") val pages: Int,
    @SerializedName("tv_shows") val tv_shows: List<ResponseTv_shows>
)
