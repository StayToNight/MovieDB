package com.staynight.moviedb.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule() {
//    @Provides
//    @Singleton
//    fun providesApplication(): Application = application
//
//    @Provides
//    @Singleton
//    fun providesApplicationContext(): Context = application
}