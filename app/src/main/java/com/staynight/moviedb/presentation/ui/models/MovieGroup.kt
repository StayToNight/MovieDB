package com.staynight.moviedb.presentation.ui.models

import com.staynight.moviedb.domain.models.Movie

data class MovieGroup(
    val title: String,
    val movies: MutableList<Movie>
)
