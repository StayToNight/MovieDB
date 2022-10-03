package com.staynight.moviedb.domain.repository

import com.staynight.moviedb.data.models.CodeMessageResponseData
import com.staynight.moviedb.domain.models.Movies
import com.staynight.moviedb.presentation.ui.models.MovieGroup
import io.reactivex.Single

interface MovieRepository {
    fun getTopRatedMovies(page: Int = 1): Single<Movies>
    fun getPopularMovies(page: Int = 1): Single<Movies>
    fun getUpcomingMovies(page: Int = 1): Single<Movies>
    fun addToWatchlist(
        mediaId: Int,
        mediaType: String,
        addToWatchlist: Boolean
    ): Single<CodeMessageResponseData>

    fun getWatchlist(): Single<Movies>
    fun getHomePage(): Single<List<MovieGroup>>
}