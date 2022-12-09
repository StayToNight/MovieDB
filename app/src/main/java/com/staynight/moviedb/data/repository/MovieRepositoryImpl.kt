package com.staynight.moviedb.data.repository

import com.staynight.moviedb.data.mappers.MoviesDataMapper
import com.staynight.moviedb.data.models.AddToWatchlistRequestBody
import com.staynight.moviedb.data.models.CodeMessageResponseData
import com.staynight.moviedb.data.network.Api
import com.staynight.moviedb.domain.models.Movies
import com.staynight.moviedb.domain.repository.MovieRepository
import io.reactivex.Single
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: Api,
    private val moviesDataMapper: MoviesDataMapper
) : MovieRepository {

    companion object {
        private const val userId = 14994776
    }

    override fun getTopRatedMovies(page: Int): Single<Movies> {
        return api.getTopRatedMovies(page)
            .map { moviesDataMapper.to(it) }
            .onErrorReturn { return@onErrorReturn Movies(null, null, null, null) }
    }

    override fun getPopularMovies(page: Int): Single<Movies> {
        return api.getPopularMovies(page)
            .map { moviesDataMapper.to(it) }
            .onErrorReturn { return@onErrorReturn Movies(null, null, null, null) }
    }

    override fun getUpcomingMovies(page: Int): Single<Movies> {
        return api.getUpcomingMovies(page)
            .map { moviesDataMapper.to(it) }
            .onErrorReturn { return@onErrorReturn Movies(null, null, null, null) }
    }

    override fun addToWatchlist(
        mediaId: Int,
        mediaType: String,
        addToWatchlist: Boolean
    ): Single<CodeMessageResponseData> {
        return api.addToWatchlist(
            id = userId,
            AddToWatchlistRequestBody(mediaId, mediaType, addToWatchlist)
        )
    }

    override fun getWatchlist(page: Int): Single<Movies> {
        return api.getWatchList(userId, page = page)
            .map { moviesDataMapper.to(it) }
    }
}