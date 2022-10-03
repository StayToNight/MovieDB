package com.staynight.moviedb.domain.models

data class Movies(
    val page: Int?,
    val results: List<Movie>?,
    val totalPages: Int?,
    val totalResults: Int?
)