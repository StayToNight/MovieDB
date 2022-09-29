package com.staynight.moviedb.domain.usecase

import com.staynight.moviedb.domain.repository.MovieRepository
import javax.inject.Inject

class GetUpcomingMoviesUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    fun getUpcomingMovies(page: Int = 1) = movieRepository.getUpcomingMovies(page)
}