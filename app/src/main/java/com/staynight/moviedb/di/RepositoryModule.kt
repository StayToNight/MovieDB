package com.staynight.moviedb.di

import com.staynight.moviedb.data.network.Api
import com.staynight.moviedb.data.repository.AuthRepositoryImpl
import com.staynight.moviedb.data.repository.MovieRepositoryImpl
import com.staynight.moviedb.domain.repository.AuthRepository
import com.staynight.moviedb.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideAuthRepository(api: Api): AuthRepository {
        return AuthRepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun provideMovieRepository(api: Api): MovieRepository {
        return MovieRepositoryImpl(api)
    }
}