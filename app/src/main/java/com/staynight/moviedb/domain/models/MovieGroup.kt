package com.staynight.moviedb.domain.models

data class MovieGroup(
    val title: String,
    val movies: MutableList<Movie>
)
