package com.arvind.moviesapp.view.home

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.arvind.moviesapp.R
import com.arvind.moviesapp.adapter.CustomAdapterMostPopular
import com.arvind.moviesapp.adapter.MostPopularComparator
import com.arvind.moviesapp.adapter.TvShowsLoadStateAdapter
import com.arvind.moviesapp.databinding.FragmentHomeBinding
import com.arvind.moviesapp.view.base.BaseFragment
import com.arvind.moviesapp.viewmodel.MostPopularTvShowsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, MostPopularTvShowsViewModel>() {
    override val viewModel: MostPopularTvShowsViewModel by activityViewModels()

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
        binding.rvTvshowsmostpopular.adapter = pagingAdapter
        binding.rvTvshowsmostpopular.setHasFixedSize(true)


        lifecycleScope.launch {
            viewModel.getListData().collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }
        binding.rvTvshowsmostpopular.adapter =
            pagingAdapter.withLoadStateHeaderAndFooter(
                header = TvShowsLoadStateAdapter { pagingAdapter.retry() },
                footer = TvShowsLoadStateAdapter { pagingAdapter.retry() }
            )

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        lifecycleScope.launchWhenStarted {
            val isChecked = viewModel.getUIMode.first()
            val uiMode = menu.findItem(R.id.action_night_mode)
            uiMode.isChecked = isChecked
            setUIMode(uiMode, isChecked)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        return when (item.itemId) {
            R.id.action_night_mode -> {
                item.isChecked = !item.isChecked
                setUIMode(item, item.isChecked)
                true
            }
            R.id.action_search -> {
                findNavController().navigate(R.id.action_homeFragment_to_SearchFragment)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setUIMode(item: MenuItem, isChecked: Boolean) {
        if (isChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            viewModel.saveToDataStore(true)
            item.setIcon(R.drawable.ic_night)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            viewModel.saveToDataStore(false)
            item.setIcon(R.drawable.ic_day)
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater, container, false)
}