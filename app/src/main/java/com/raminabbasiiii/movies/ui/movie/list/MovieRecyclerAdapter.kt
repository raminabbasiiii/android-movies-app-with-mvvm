package com.raminabbasiiii.movies.ui.movie.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.raminabbasiiii.movies.databinding.MovieItemBinding
import com.raminabbasiiii.movies.model.Movie
import com.raminabbasiiii.movies.util.MovieDiffCallback
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MovieRecyclerAdapter
@Inject
constructor(
    @ApplicationContext val context: Context,
    private val clicked: (Int) -> Unit
    ): PagingDataAdapter<Movie, MovieRecyclerAdapter.MovieViewHolder>(
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
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Movie?) {
            binding.title.text = data?.title
            binding.rating.text = data?.rating
            binding.country.text = data?.country

            Glide.with(binding.root)
                .load(data?.poster)
                .placeholder(circularProgressBar())
                .transition(DrawableTransitionOptions.withCrossFade())
                .fitCenter()
                .into(binding.poster)

            binding.root.setOnClickListener {
                data?.id?.let { id -> clicked.invoke(id) }
            }

        }

        private fun circularProgressBar(): CircularProgressDrawable {
            val circularProgressDrawable = CircularProgressDrawable(context)
            circularProgressDrawable.strokeWidth = 10f
            circularProgressDrawable.centerRadius = 40f
            circularProgressDrawable.start()
            return circularProgressDrawable
        }
    }
}