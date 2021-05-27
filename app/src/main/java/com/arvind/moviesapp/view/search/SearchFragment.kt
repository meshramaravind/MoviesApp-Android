package com.arvind.moviesapp.view.search

import android.os.Bundle
import android.view.*
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.arvind.moviesapp.adapter.CustomAdapterMostPopular
import com.arvind.moviesapp.adapter.MostPopularComparator
import com.arvind.moviesapp.adapter.TvShowsLoadStateAdapter
import com.arvind.moviesapp.databinding.FragmentSearchTvshowsBinding
import com.arvind.moviesapp.view.base.BaseFragment
import com.arvind.moviesapp.viewmodel.SearchTvShowsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchTvshowsBinding, SearchTvShowsViewModel>() {
    override val viewModel: SearchTvShowsViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inits()
    }

    private fun inits() = with(binding) {
        val pagingAdapter = CustomAdapterMostPopular(MostPopularComparator)
        binding.tvShowsRecyclerView.adapter = pagingAdapter
        binding.tvShowsRecyclerView.setHasFixedSize(true)


        lifecycleScope.launch {
            viewModel.getListSearchData().collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }
        binding.tvShowsRecyclerView.adapter =
            pagingAdapter.withLoadStateHeaderAndFooter(
                header = TvShowsLoadStateAdapter { pagingAdapter.retry() },
                footer = TvShowsLoadStateAdapter { pagingAdapter.retry() }
            )
    }


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSearchTvshowsBinding.inflate(inflater, container, false)
}