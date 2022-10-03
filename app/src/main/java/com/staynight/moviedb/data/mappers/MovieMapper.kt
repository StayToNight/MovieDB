package com.staynight.moviedb.data.mappers

import com.staynight.moviedb.data.models.MovieData
import com.staynight.moviedb.domain.Mapper
import com.staynight.moviedb.domain.models.Movie

class MovieMapper : Mapper<Movie, MovieData> {
    override fun from(model: Movie): MovieData = with(model) {
        return MovieData(
            adult,
            backdropPath,
            genreIds,
            id,
            originalLanguage,
            originalTitle,
            overview,
            popularity,
            posterPath,
            releaseDate,
            title,
            video,
            voteAverage,
            voteCount
        )
    }

    override fun to(model: MovieData): Movie = with(model) {
        return Movie(
            adult,
            backdropPath,
            genreIds,
            id,
            originalLanguage,
            originalTitle,
            overview,
            popularity,
            posterPath,
            releaseDate,
            title,
            video,
            voteAverage,
            voteCount
        )
    }
}