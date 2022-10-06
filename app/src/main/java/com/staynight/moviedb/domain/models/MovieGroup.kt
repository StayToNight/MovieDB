package com.staynight.moviedb.domain.models

data class MovieGroup(
    val title: String,
    var movies: MutableList<Movie>,
    val itemType: Int
)
