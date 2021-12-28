package com.raminabbasiiii.paging3.model

data class Meta(
    val currentPage: String,
    val perPage: Int,
    val pageCount: Int,
    val totalCount: Int,
)