package com.raminabbasiiii.paging3.ui.movie.list

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.raminabbasiiii.paging3.databinding.FragmentMovieListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private val viewModel: MovieListViewModel by viewModels()
    private lateinit var binding: FragmentMovieListBinding
    private lateinit var adapter : MovieRecyclerAdapter

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

        initAdapter()
        initSwipeToRefresh()
    }

    private fun snackBarClickedMovie(title: String) {
        val parentLayout = requireActivity().findViewById<View>(android.R.id.content)
        Snackbar.make(parentLayout, title, Snackbar.LENGTH_LONG)
            .show()
    }

    @OptIn(ExperimentalPagingApi::class)
    private fun initAdapter() {
        adapter = MovieRecyclerAdapter(requireContext()) { title: String -> snackBarClickedMovie(title) }
        binding.movieRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        binding.movieRecyclerView.adapter = adapter.withLoadStateFooter(
            footer = MovieLoadingStateAdapter(adapter::retry)
        )

        /*lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collect { loadStates ->
                binding.swipeRefreshLayout.isRefreshing = loadStates.source?.refresh is LoadState.Loading
            }
        }*/

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            adapter.addLoadStateListener { loadState ->
                if (loadState.source.refresh is LoadState.Loading) {
                    if (adapter.snapshot().isEmpty()) {
                        binding.progressBar.isVisible = true
                    }
                    binding.txtError.isVisible = false
                } else {
                    binding.progressBar.isVisible = false
                    binding.swipeRefreshLayout.isRefreshing = false

                    val error = when {
                        loadState.source.prepend is LoadState.Error -> loadState.source.prepend as LoadState.Error
                        loadState.source.append is LoadState.Error -> loadState.source.append as LoadState.Error
                        loadState.source.refresh is LoadState.Error -> loadState.source.refresh as LoadState.Error
                        else -> null
                    }
                    error?.let {
                        if (adapter.snapshot().isEmpty()) {
                            binding.txtError.isVisible = true
                            binding.txtError.text = it.error.localizedMessage
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.movies.collectLatest {
                adapter.submitData(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binding.movieRecyclerView.scrollToPosition(0) }
        }
    }

    private fun initSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener { adapter.refresh() }
    }


}