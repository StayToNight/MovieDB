package com.staynight.moviedb.domain.repository

import com.staynight.moviedb.data.models.MoviesData
import io.reactivex.Observable

interface MovieRepository {
    fun getTopRatedMovies() : Observable<MoviesData>
}