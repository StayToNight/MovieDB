package com.staynight.moviedb.domain.usecase

import com.staynight.moviedb.domain.repository.MovieRepository
import javax.inject.Inject

class GetTopRatedMoviesUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    fun getTopRatedMovies(page: Int = 1) = movieRepository.getTopRatedMovies(page)
}