package com.staynight.moviedb.presentation.ui.models

import com.staynight.moviedb.data.models.Movie

data class MovieGroup(
    val title: String,
    val movies: List<Movie>
)
