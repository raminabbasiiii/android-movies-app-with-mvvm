package com.raminabbasiiii.movies.ui.movie.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.raminabbasiiii.movies.databinding.FragmentMovieListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private val viewModel: MovieListViewModel by viewModels()
    private lateinit var binding: FragmentMovieListBinding
    private lateinit var recyclerAdapter : MovieRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        subscribeObservers()
        retry()
    }

    private fun initRecyclerView() {

        binding.movieRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            recyclerAdapter =
                MovieRecyclerAdapter(requireContext()) { movieId: Int ->
                    findNavController().navigate(
                        MovieListFragmentDirections
                            .actionMovieListFragmentToMovieDetailsFragment(movieId = movieId )
                    )
                }

            adapter = recyclerAdapter

        }

        binding.movieRecyclerView.adapter = recyclerAdapter.withLoadStateFooter(
            footer = MovieLoadingStateAdapter(recyclerAdapter::retry)
        )

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            recyclerAdapter.addLoadStateListener { loadState ->
                if (loadState.mediator?.refresh is LoadState.Loading) {

                    if (recyclerAdapter.snapshot().isEmpty()) {
                        binding.progressBar.isVisible = true
                    }
                    binding.txtError.isVisible = false
                    binding.btnRetry.isVisible = false
                } else {
                    binding.progressBar.isVisible = false

                    val error = when {
                        loadState.mediator?.prepend is LoadState.Error -> loadState.mediator?.prepend as LoadState.Error
                        loadState.mediator?.append is LoadState.Error -> loadState.mediator?.append as LoadState.Error
                        loadState.mediator?.refresh is LoadState.Error -> loadState.mediator?.refresh as LoadState.Error
                        else -> null
                    }
                    error?.let {
                        if (recyclerAdapter.snapshot().isEmpty()) {
                            binding.txtError.isVisible = true
                            binding.btnRetry.isVisible = true
                            binding.txtError.text = "Check Network Connection!"
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            recyclerAdapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
        }
    }

    private fun subscribeObservers() {
        viewModel.movies.observe(viewLifecycleOwner){
            recyclerAdapter.submitData(lifecycle,it)
        }
    }

    private fun retry() {
        binding.btnRetry.setOnClickListener { recyclerAdapter.retry() }
    }

}