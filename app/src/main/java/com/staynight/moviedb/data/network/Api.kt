package com.staynight.moviedb.data.network

import com.staynight.moviedb.data.models.MoviesData
import com.staynight.moviedb.data.models.RequestTokenData
import io.reactivex.Observable
import retrofit2.http.GET

interface Api {
    @GET("authentication/token/new")
    fun getRequestToken(): Observable<RequestTokenData>

    @GET("movie/top_rated")
    fun getTopRatedMovies(): Observable<MoviesData>

}

