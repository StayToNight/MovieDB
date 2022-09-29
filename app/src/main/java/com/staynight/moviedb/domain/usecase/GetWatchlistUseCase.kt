package com.staynight.moviedb.domain.usecase

import com.staynight.moviedb.domain.repository.MovieRepository
import javax.inject.Inject

class GetWatchlistUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    fun getWatchlist() = movieRepository.getWatchlist()
}