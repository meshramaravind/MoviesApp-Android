package com.arvind.moviesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.arvind.moviesapp.R
import com.arvind.moviesapp.databinding.ItemContainerSliderImageBinding

class ImageSliderAdapter(private var sliderImages: List<String>) :
    RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder>() {
    private var layoutInflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSliderViewHolder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        val itemContainerSliderImageBinding: ItemContainerSliderImageBinding =
            DataBindingUtil.inflate(
                layoutInflater!!, R.layout.item_container_slider_image, parent, false
            )
        return ImageSliderViewHolder(itemContainerSliderImageBinding)
    }

    override fun onBindViewHolder(holder: ImageSliderViewHolder, position: Int) {
        holder.bindSliderImage(sliderImages[position])
    }

    override fun getItemCount(): Int {
        return sliderImages.size
    }

    class ImageSliderViewHolder(itemContainerSliderImageBinding: ItemContainerSliderImageBinding) :
        RecyclerView.ViewHolder(itemContainerSliderImageBinding.getRoot()) {
        private val itemContainerSliderImageBinding: ItemContainerSliderImageBinding
        fun bindSliderImage(imageURL: String?) {
            itemContainerSliderImageBinding.setImageURL(imageURL)
        }

        init {
            this.itemContainerSliderImageBinding = itemContainerSliderImageBinding
        }
    }
}