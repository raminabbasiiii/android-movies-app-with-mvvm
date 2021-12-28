package com.raminabbasiiii.paging3.datasource.network.response

import com.google.gson.annotations.SerializedName
import com.raminabbasiiii.paging3.model.Meta
import com.raminabbasiiii.paging3.datasource.db.entity.Movie

data class Movies(
    @SerializedName("data")
    val movies: List<Movie>,

    @SerializedName("metadata")
    val meta: Meta
    )