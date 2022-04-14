package com.raminabbasiiii.movies.util

import androidx.recyclerview.widget.DiffUtil
import com.raminabbasiiii.movies.data.db.movie.MovieEntity

object MovieDiffCallback : DiffUtil.ItemCallback<MovieEntity>() {

    override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
        return oldItem == newItem
    }
}