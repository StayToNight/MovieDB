package com.staynight.moviedb.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StorageModule {
    companion object{
        private const val SHARED_PREF = "shared_pref"
    }
    @Singleton
    @Provides
    fun provideSharedPref(@ApplicationContext context: Context) : SharedPreferences = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
}