package com.raminabbasiiii.movies.data.network.responses

import com.google.gson.annotations.SerializedName
import com.raminabbasiiii.movies.model.MetaData
import com.raminabbasiiii.movies.data.network.MovieDto

data class MovieListResponse(
    @SerializedName("data")
    val data: List<MovieDto>,

    @SerializedName("metadata")
    val metaData: MetaData
    )