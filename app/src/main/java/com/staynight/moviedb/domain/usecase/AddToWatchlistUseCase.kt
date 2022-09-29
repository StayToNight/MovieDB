package com.staynight.moviedb.domain.usecase

import com.staynight.moviedb.domain.repository.MovieRepository
import javax.inject.Inject

class AddToWatchlistUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    fun addToWatchList(mediaId: Int, mediaType: String, addToWatchList: Boolean) =
        movieRepository.addToWatchlist(mediaId, mediaType, addToWatchList)
}