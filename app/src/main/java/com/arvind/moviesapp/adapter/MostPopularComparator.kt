package com.arvind.moviesapp.adapter

import androidx.recyclerview.widget.DiffUtil
import com.arvind.moviesapp.response.popular.ResponseTv_shows


object MostPopularComparator : DiffUtil.ItemCallback<ResponseTv_shows>() {
    override fun areItemsTheSame(
        oldItem: ResponseTv_shows,
        newItem: ResponseTv_shows
    ): Boolean {
        // Id is unique.
        return oldItem.id == newItem.id

    }

    override fun areContentsTheSame(
        oldItem: ResponseTv_shows,
        newItem: ResponseTv_shows
    ): Boolean {
        return oldItem == newItem
    }
}