package com.raminabbasiiii.movies.ui.movie.details

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.material.appbar.AppBarLayout
import com.raminabbasiiii.movies.databinding.FragmentMovieDetailsBinding
import com.raminabbasiiii.movies.util.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding
    private val viewModel: MovieDetailsViewModel by viewModels()
    private val args: MovieDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        initToolbar()
        btnCloseOnclick()
        subscribeObservers()
        retry()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,callback)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            findNavController().popBackStack()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.moviesDetailsToolbar)
        (activity as AppCompatActivity).supportActionBar?.apply {
            title = null
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    private fun subscribeObservers() {
        viewModel.setMovieId(args.movieId)
        viewModel.movieDetails.observe(viewLifecycleOwner) { result ->
            when(result.status) {
                Status.LOADING -> {
                    binding.progressBar.isVisible = true
                }
                Status.SUCCESS -> {
                    binding.progressBar.isVisible = false
                    binding.coordinatorLayout.isVisible = true
                    binding.txtError.isVisible = false
                    binding.btnRetry.isVisible = false
                    result.data?.let { movieDetails ->
                        movieDetails.images?.let { images -> initImageSlider(images) }
                        setMovieProperties(
                            movieDetails.actors,movieDetails.awards,movieDetails.country,movieDetails.director,
                            movieDetails.plot,movieDetails.rating,movieDetails.released,movieDetails.runtime,
                            movieDetails.title,movieDetails.writer,movieDetails.year, movieDetails.genres!!)
                    }
                }
                Status.ERROR -> {
                    binding.coordinatorLayout.isVisible = false
                    binding.progressBar.isVisible = false
                    binding.txtError.isVisible = true
                    binding.btnRetry.isVisible = true
                    binding.txtError.text = "Check Network Connection!"
                }
            }
        }
    }

    private fun initImageSlider(images: List<String>) {
        val imageList = ArrayList<SlideModel>()
        for(image in images) {
            imageList.add(SlideModel(image))
        }
        binding.imageSlider.setImageList(imageList, ScaleTypes.FIT)
        binding.imageSlider.setItemClickListener(object : ItemClickListener {
            override fun onItemSelected(position: Int) {
                binding.moviesDetailsToolbar.isVisible = false
                binding.fullImageSlider.isVisible = true
                binding.btnClose.isVisible = true
                binding.fullImageSlider.setImageList(imageList, ScaleTypes.CENTER_INSIDE)
            }
        })
    }

    private fun setCollapseToolbarTitle(title: String) {
        var isShow = true
        var scrollRange = -1
        binding.appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { barLayout, verticalOffset ->
            if (scrollRange == -1){
                scrollRange = barLayout?.totalScrollRange!!
            }
            if (scrollRange + verticalOffset == 0){
                binding.collapsingToolbar.title = title
                isShow = true
            } else if (isShow){
                binding.collapsingToolbar.title = " " //careful there should a space between double quote otherwise it wont work
                isShow = false
            }
        })
    }

    fun setMovieProperties(
        actors: String, awards: String, country: String, director: String, plot: String,
        rating: String, released: String, runtime: String, title: String, writer: String,
        year: String, genres: List<String>)
    {
        setCollapseToolbarTitle(title)
        binding.tvActors.text = "Actors : $actors"
        binding.tvAwards.text = "Awards : $awards"
        binding.tvCountry.text = "Country : $country"
        binding.tvDirector.text = "Director : $director"
        binding.tvPlot.text = "Plot : $plot"
        binding.tvRating.text = "Rating : $rating"
        binding.tvReleased.text = "Released : $released"
        binding.tvRuntime.text = "Runtime : $runtime"
        binding.tvTitle.text = title
        binding.tvWriter.text = "Writer : $writer"
        binding.tvYear.text = "Year : $year"
        var allGenres = ""
        for (genre in genres) {
            allGenres += "$genre, "
        }
        binding.tvGenres.text = "Genres : $allGenres"
    }

    private fun btnCloseOnclick() {
        binding.btnClose.setOnClickListener {
            binding.fullImageSlider.isVisible = false
            binding.btnClose.isVisible = false
            binding.moviesDetailsToolbar.isVisible = true
        }
    }

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if(binding.fullImageSlider.isVisible) {
                binding.fullImageSlider.isVisible = false
                binding.btnClose.isVisible = false
                binding.moviesDetailsToolbar.isVisible = true
            } else
                findNavController().popBackStack()
        }
    }

    private fun retry() {
        binding.btnRetry.also {
            it.setOnClickListener { subscribeObservers() }
        }
    }
}









