package com.raminabbasiiii.movies.util

import androidx.recyclerview.widget.DiffUtil
import com.raminabbasiiii.movies.model.Movie

object MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}