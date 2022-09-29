package com.staynight.moviedb.data.repository

import com.staynight.moviedb.data.models.AddToWatchlistRequestBody
import com.staynight.moviedb.data.models.CodeMessageResponseData
import com.staynight.moviedb.data.models.MoviesData
import com.staynight.moviedb.data.network.Api
import com.staynight.moviedb.domain.repository.MovieRepository
import io.reactivex.Single
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val api: Api): MovieRepository {

    companion object {
        private const val userId = 14994776
    }

    override fun getTopRatedMovies(page: Int): Single<MoviesData> {
        return api.getTopRatedMovies(page)
    }

    override fun getPopularMovies(page: Int): Single<MoviesData> {
        return api.getPopularMovies(page)
    }

    override fun getUpcomingMovies(page: Int): Single<MoviesData> {
        return api.getUpcomingMovies(page)
    }

    override fun addToWatchlist(
        mediaId: Int,
        mediaType: String,
        addToWatchlist: Boolean
    ): Single<CodeMessageResponseData> {
        return api.addToWatchlist(id = userId, AddToWatchlistRequestBody(mediaId, mediaType, addToWatchlist))
    }

    override fun getWatchlist(): Single<MoviesData> {
        return api.getWatchList(userId)
    }
}