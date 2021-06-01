package com.arvind.moviesapp.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.arvind.moviesapp.databinding.ItemsMostPopularTvshowsBinding
import com.arvind.moviesapp.response.popular.ResponseTv_shows
import com.arvind.moviesapp.view.home.HomeFragmentDirections


class CustomAdapterMostPopular(
    diffCallback: DiffUtil.ItemCallback<ResponseTv_shows>
) :
    PagingDataAdapter<ResponseTv_shows, CustomAdapterMostPopular.MyViewHolder>(
        diffCallback
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MyViewHolder(
            ItemsMostPopularTvshowsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class MyViewHolder(private val binding: ItemsMostPopularTvshowsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(responsetvShows: ResponseTv_shows) = with(binding) {
            mostpopulartvshows = responsetvShows
            binding.root.setOnClickListener { v ->
                val direction = HomeFragmentDirections
                    .actionHomeFragmentToDetailsFragment(responsetvShows)
                v.findNavController().navigate(direction)
            }

        }
    }

}