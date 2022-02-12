package com.raminabbasiiii.paging3.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.raminabbasiiii.paging3.databinding.MovieItemBinding
import com.raminabbasiiii.paging3.model.Movie
import com.raminabbasiiii.paging3.util.MovieDiffCallback

class MovieRecyclerAdapter(private val clicked: (String) -> Unit) :
    PagingDataAdapter<Movie, MovieRecyclerAdapter.MovieViewHolder>(
        MovieDiffCallback
    ) {

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    inner class MovieViewHolder(
        private val binding: MovieItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Movie?) {
            binding.title.text = data?.title
            binding.year.text = data?.year
            binding.country.text = data?.country

            Glide.with(binding.root)
                .load(data?.poster)
                .transition(DrawableTransitionOptions.withCrossFade())
                .fitCenter()
                .into(binding.poster)

            binding.root.setOnClickListener {
                data?.title?.let { it1 -> clicked.invoke(it1) }
            }

        }
    }
}