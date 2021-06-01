package com.arvind.moviesapp.response.popular

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseTv_shows(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("permalink") val permalink: String,
    @SerializedName("start_date") val start_date: String,
    @SerializedName("end_date") val end_date: String,
    @SerializedName("country") val country: String,
    @SerializedName("network") val network: String,
    @SerializedName("status") val status: String,
    @SerializedName("image_thumbnail_path") val image_thumbnail_path: String
) : Parcelable
