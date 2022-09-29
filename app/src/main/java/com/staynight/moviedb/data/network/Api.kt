package com.staynight.moviedb.data.network

import com.staynight.moviedb.data.models.*
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("authentication/token/new")
    fun getRequestToken(): Single<RequestTokenData>

    @GET("movie/top_rated")
    fun getTopRatedMovies(@Query("page") page: Int = 1): Single<MoviesData>

    @GET("movie/popular")
    fun getPopularMovies(@Query("page") page: Int = 1): Single<MoviesData>

    @GET("movie/upcoming")
    fun getUpcomingMovies(@Query("page") page: Int = 1): Single<MoviesData>

    @POST("authentication/token/validate_with_login")
    fun authWithLogin(@Body authRequestBody: AuthRequestBody): Single<AuthWithLoginResponseData>

    @POST("authentication/session/new")
    fun createSession(@Body createNewSessionRequestBody: CreateNewSessionRequestBody): Single<CreateNewSessionResponseData>

    @POST("account/{account_id}/watchlist")
    fun addToWatchlist(@Path("account_id") id: Int, @Body addToWatchlistRequestBody: AddToWatchlistRequestBody): Single<CodeMessageResponseData>

    @GET("account/{account_id}/watchlist/movies")
    fun getWatchList(@Path("account_id") id: Int): Single<MoviesData>

}

