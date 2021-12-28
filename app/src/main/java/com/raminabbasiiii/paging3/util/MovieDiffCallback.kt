package com.raminabbasiiii.paging3.util

import androidx.recyclerview.widget.DiffUtil
import com.raminabbasiiii.paging3.datasource.db.entity.Movie

object MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}