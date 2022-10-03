package com.staynight.moviedb.di

import com.staynight.moviedb.data.mappers.MovieMapper
import com.staynight.moviedb.data.mappers.MoviesDataMapper
import dagger.Module
import dagger.Provides

@Module
class MapperModule {
    @Provides
    fun provideMovieMapper() : MovieMapper = MovieMapper()

    @Provides
    fun provideMoviesMapper(movieMapper: MovieMapper) : MoviesDataMapper = MoviesDataMapper(movieMapper)
}