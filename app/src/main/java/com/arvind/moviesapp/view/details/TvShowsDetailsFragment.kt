package com.arvind.moviesapp.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.arvind.moviesapp.databinding.FragmentTvShowsDetailsBinding
import com.arvind.moviesapp.view.base.BaseFragment
import com.arvind.moviesapp.viewmodel.TvShowsDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvShowsDetailsFragment :
    BaseFragment<FragmentTvShowsDetailsBinding, TvShowsDetailsViewModel>() {
    override val viewModel: TvShowsDetailsViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentTvShowsDetailsBinding.inflate(inflater, container, false)

}