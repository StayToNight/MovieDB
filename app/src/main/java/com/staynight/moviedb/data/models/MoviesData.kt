package com.staynight.moviedb.data.models

data class MoviesData(
    val page: Int?,
    val results: List<Movie>?,
    val total_pages: Int?,
    val total_results: Int?
)