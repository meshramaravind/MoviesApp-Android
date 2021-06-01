package com.arvind.moviesapp.view.details

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.arvind.moviesapp.R
import com.arvind.moviesapp.adapter.CustomEpisodeAdapter
import com.arvind.moviesapp.adapter.ImageSliderAdapter
import com.arvind.moviesapp.databinding.FragmentTvShowsDetailsBinding
import com.arvind.moviesapp.databinding.LayoutEpisodeBottomSheetBinding
import com.arvind.moviesapp.response.popular.ResponseTv_shows
import com.arvind.moviesapp.response.showdetails.ResponseTvShowDetails
import com.arvind.moviesapp.view.base.BaseFragment
import com.arvind.moviesapp.viewmodel.TvShowsDetailsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class TvShowsDetailsFragment :
    BaseFragment<FragmentTvShowsDetailsBinding, TvShowsDetailsViewModel>() {
    override val viewModel: TvShowsDetailsViewModel by activityViewModels()
    private val args: TvShowsDetailsFragmentArgs by navArgs()
    private lateinit var responsetvShows: ResponseTv_shows
    private var episodeBottomSheetDialog: BottomSheetDialog? = null
    private lateinit var layoutEpisodesBottomSheetBinding: LayoutEpisodeBottomSheetBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        responsetvShows = args.responsetvShows
        initsview()

        

    }

    private fun initsview() = with(binding) {
        binding.setIsLoading(true)
        viewModel.getTVShowDetails(responsetvShows.id)
            ?.observe(requireActivity()) { tvShowDetailsResponse ->
                binding.setIsLoading(false)
                if (tvShowDetailsResponse != null) {
                    loadImageSlider(tvShowDetailsResponse.tvShow.pictures)
                    binding.setTvShowImageURL(
                        tvShowDetailsResponse.tvShow.image_path
                    )
                    binding.imageTVShow.setVisibility(View.VISIBLE)

                    binding.buttonEpisodes.setOnClickListener {
                        Log.d("btnSetup", "Selected")
                        if (episodeBottomSheetDialog == null) {
                            episodeBottomSheetDialog =
                                BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
                            layoutEpisodesBottomSheetBinding =
                                DataBindingUtil.inflate(
                                    LayoutInflater.from(requireContext()),
                                    R.layout.layout_episode_bottom_sheet,
                                    it.findViewById(R.id.episodesContainer),
                                    false
                                )
                            episodeBottomSheetDialog!!.setContentView(
                                layoutEpisodesBottomSheetBinding!!.root
                            )

                            layoutEpisodesBottomSheetBinding.episodesRecyclerView.setAdapter(
                                CustomEpisodeAdapter(tvShowDetailsResponse.tvShow.episodes)
                            )
                            layoutEpisodesBottomSheetBinding.textTitle.setText(
                                java.lang.String.format(
                                    "Episode | %s",
                                    tvShowDetailsResponse.tvShow.name
                                )
                            )
                            layoutEpisodesBottomSheetBinding.imageClose.setOnClickListener { v1 -> episodeBottomSheetDialog!!.dismiss() }


                        }
                        episodeBottomSheetDialog!!.show()
                    }

                    loadBasicTVShowDetails(tvShowDetailsResponse.tvShow)
                }
            }
    }


    private fun loadImageSlider(pictures: List<String>) {
        binding.sliderView.setOffscreenPageLimit(1)
        binding.sliderView.setAdapter(ImageSliderAdapter(pictures))
        binding.sliderView.setVisibility(View.VISIBLE)
        binding.viewFadingEdge.setVisibility(View.VISIBLE)
        setupSliderIndicator(pictures.size)
        binding.sliderView.registerOnPageChangeCallback(object :
            OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setupCurrentSliderIndicator(position)
            }
        })

    }

    private fun setupSliderIndicator(count: Int) {
        val indicators = arrayOfNulls<ImageView>(count)
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(requireContext())
            indicators[i]!!.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(), R.drawable.background_slider_indicator_inactive
                )
            )
            indicators[i]!!.layoutParams = layoutParams
            binding.layoutSliderIndicators.addView(indicators[i])
        }
        binding.layoutSliderIndicators.setVisibility(View.VISIBLE)
        setupCurrentSliderIndicator(0)

    }

    private fun setupCurrentSliderIndicator(position: Int) {
        val childCount: Int = binding.layoutSliderIndicators.getChildCount()
        for (i in 0 until childCount) {
            val imageView =
                binding.layoutSliderIndicators.getChildAt(i) as ImageView
            if (i == position) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(), R.drawable.background_slider_indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(), R.drawable.background_slider_indicator_inactive
                    )
                )
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun loadBasicTVShowDetails(tvShowDetails: ResponseTvShowDetails) {
        binding.setTvShowName(tvShowDetails.name)
        binding.textName.setVisibility(View.VISIBLE)

        binding.setStartedDate(tvShowDetails.start_date)
        binding.textStarted.setVisibility(View.VISIBLE)

        binding.setNetworkCountry(
            tvShowDetails.network.toString() +
                    " (" + tvShowDetails.country + ")"
        )
        binding.textNetworkCountry.setVisibility(View.VISIBLE)

        binding.setStatus(tvShowDetails.status)
        binding.textStatus.setVisibility(View.VISIBLE)

        binding.setDescription(
            HtmlCompat.fromHtml(
                tvShowDetails.description,
                HtmlCompat.FROM_HTML_MODE_LEGACY
            ).toString()
        )
        binding.textDescription.setVisibility(View.VISIBLE)

        binding.textReadMore.setVisibility(View.VISIBLE)
        binding.textReadMore.setOnClickListener { v ->
            if (binding.textReadMore.getText().toString()
                    .equals("more")
            ) {
                binding.textDescription.setMaxLines(Int.MAX_VALUE)
                binding.textDescription.setEllipsize(null)
                binding.textReadMore.setText("less")
            } else {
                binding.textDescription.setMaxLines(4)
                binding.textDescription.setEllipsize(TextUtils.TruncateAt.END)
                binding.textReadMore.setText("more")
            }
        }

        binding.rating =
            String.format(Locale.getDefault(), "%.2f", tvShowDetails.rating.toDouble())

        binding.setGenre(tvShowDetails.genres.get(0))
        binding.setRuntime(tvShowDetails.runtime.toString() + " Min")
        binding.viewDivider1.setVisibility(View.VISIBLE)
        binding.layoutMisc.setVisibility(View.VISIBLE)
        binding.viewDivider2.setVisibility(View.VISIBLE)

        binding.buttonWebsite.setOnClickListener { v ->
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(tvShowDetails.url)
            startActivity(intent)
        }
        binding.buttonWebsite.setVisibility(View.VISIBLE)
        binding.buttonEpisodes.setVisibility(View.VISIBLE)

    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentTvShowsDetailsBinding.inflate(inflater, container, false)

}