package com.staynight.moviedb.data.repository

import com.staynight.moviedb.data.models.MoviesData
import com.staynight.moviedb.data.network.Api
import com.staynight.moviedb.domain.repository.MovieRepository
import io.reactivex.Observable
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val api: Api): MovieRepository {
    override fun getTopRatedMovies(): Observable<MoviesData> {
        return api.getTopRatedMovies()
    }
}