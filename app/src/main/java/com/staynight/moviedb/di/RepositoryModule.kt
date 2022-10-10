package com.staynight.moviedb.di

import com.staynight.moviedb.data.mappers.MoviesDataMapper
import com.staynight.moviedb.data.network.Api
import com.staynight.moviedb.data.repository.AuthRepositoryImpl
import com.staynight.moviedb.data.repository.MovieRepositoryImpl
import com.staynight.moviedb.domain.repository.AuthRepository
import com.staynight.moviedb.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideAuthRepository(api: Api): AuthRepository {
        return AuthRepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun provideMovieRepository(api: Api, moviesDataMapper: MoviesDataMapper): MovieRepository {
        return MovieRepositoryImpl(api, moviesDataMapper)
    }
}