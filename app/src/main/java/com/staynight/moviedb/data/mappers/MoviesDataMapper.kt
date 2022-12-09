package com.staynight.moviedb.data.mappers

import com.staynight.moviedb.data.models.MoviesData
import com.staynight.moviedb.domain.Mapper
import com.staynight.moviedb.domain.models.Movies
import javax.inject.Inject

class MoviesDataMapper @Inject constructor(private val movieMapper: MovieMapper) :
    Mapper<Movies, MoviesData> {
    override fun from(model: Movies): MoviesData = with(model) {
        return MoviesData(
            page,
            results?.map { movie -> movieMapper.from(movie) },
            totalPages,
            totalResults
        )
    }

    override fun to(model: MoviesData): Movies = with(model) {
        return Movies(
            page,
            results?.map { movieData -> movieMapper.to(movieData) },
            totalPages,
            totalResults
        )
    }
}