package com.staynight.moviedb.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides

@Module
class StorageModule {
    companion object{
        private const val SHARED_PREF = "shared_pref"
    }
    @Provides
    fun provideSharedPref(context: Context) : SharedPreferences = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
}