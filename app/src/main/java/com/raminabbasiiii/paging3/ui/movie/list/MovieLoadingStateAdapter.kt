package com.raminabbasiiii.paging3.ui.movie.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.raminabbasiiii.paging3.R
import com.raminabbasiiii.paging3.databinding.MovieLoadingStateItemBinding

class MovieLoadingStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<MovieLoadingStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup, loadState: LoadState
    ) = LoadStateViewHolder(parent,retry)


    override fun onBindViewHolder(
        holder: LoadStateViewHolder, loadState: LoadState
    ) = holder.bind(loadState)

    inner class LoadStateViewHolder(
        parent: ViewGroup,
        retry: () -> Unit
        ): RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_loading_state_item,parent,false)
        ) {

        private val binding = MovieLoadingStateItemBinding.bind(itemView)
        private val retry: Button = binding.retryButton
            .also {
                it.setOnClickListener { retry() }
            }

            fun bind(loadState: LoadState) {

                if (loadState is LoadState.Error) {
                    binding.errorMsg.text = loadState.error.localizedMessage
                }
                binding.progressBar.isVisible = loadState is LoadState.Loading
                binding.retryButton.isVisible = loadState is LoadState.Error
                binding.errorMsg.isVisible = loadState is LoadState.Error
            }
        }
}