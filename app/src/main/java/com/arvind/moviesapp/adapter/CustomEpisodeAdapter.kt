package com.arvind.moviesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.arvind.moviesapp.R
import com.arvind.moviesapp.databinding.ItemContainerEpisodeBinding
import com.arvind.moviesapp.response.episode.ResponseEpisodes

class CustomEpisodeAdapter( private var episodeList: List<ResponseEpisodes>? = null) : RecyclerView.Adapter<CustomEpisodeAdapter.EpisodeViewHolder>() {

    private var layoutInflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        val itemContainerEpisodeBinding: ItemContainerEpisodeBinding = DataBindingUtil.inflate(
            layoutInflater!!, R.layout.item_container_episode, parent, false
        )
        return EpisodeViewHolder(itemContainerEpisodeBinding)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.bindEpisode(episodeList!![position])
    }

    override fun getItemCount(): Int {
        return episodeList!!.size
    }


    class EpisodeViewHolder(itemContainerEpisodeBinding: ItemContainerEpisodeBinding) :
        RecyclerView.ViewHolder(itemContainerEpisodeBinding.getRoot()) {
        private val itemContainerEpisodeBinding: ItemContainerEpisodeBinding
        fun bindEpisode(episode: ResponseEpisodes) {
            var title = "S"
            var season: String = java.lang.String.valueOf(episode.season)
            if (season.length == 1) {
                season = "0$season"
            }
            var episodeNumber: String = java.lang.String.valueOf(episode.episode)
            if (episodeNumber.length == 1) {
                episodeNumber = "0$episodeNumber"
            }
            episodeNumber = "E$episodeNumber"
            title = title + season + episodeNumber
            itemContainerEpisodeBinding.setTitle(title)
            itemContainerEpisodeBinding.name = episode.name
            itemContainerEpisodeBinding.airDate = episode.air_date
        }

        init {
            this.itemContainerEpisodeBinding = itemContainerEpisodeBinding
        }
    }
}