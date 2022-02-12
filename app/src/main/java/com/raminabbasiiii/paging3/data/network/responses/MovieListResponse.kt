package com.raminabbasiiii.paging3.data.network.responses

import com.google.gson.annotations.SerializedName
import com.raminabbasiiii.paging3.model.MetaData
import com.raminabbasiiii.paging3.data.network.MovieDto

data class MovieListResponse(
    @SerializedName("data")
    val data: List<MovieDto>,

    @SerializedName("metadata")
    val metaData: MetaData
    )