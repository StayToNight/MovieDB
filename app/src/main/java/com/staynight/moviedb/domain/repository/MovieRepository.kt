package com.staynight.moviedb.domain.repository

import com.staynight.moviedb.data.models.CodeMessageResponseData
import com.staynight.moviedb.data.models.MoviesData
import io.reactivex.Single

interface MovieRepository {
    fun getTopRatedMovies(page: Int = 1) : Single<MoviesData>
    fun getPopularMovies(page: Int = 1) : Single<MoviesData>
    fun getUpcomingMovies(page: Int = 1) : Single<MoviesData>
    fun addToWatchlist(mediaId: Int, mediaType: String, addToWatchlist: Boolean): Single<CodeMessageResponseData>
    fun getWatchlist(): Single<MoviesData>
}