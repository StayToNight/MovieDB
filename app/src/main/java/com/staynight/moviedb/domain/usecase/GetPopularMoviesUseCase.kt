package com.staynight.moviedb.domain.usecase

import com.staynight.moviedb.domain.repository.MovieRepository
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    fun getPopularMovies(page: Int = 1) = movieRepository.getPopularMovies(1)
}