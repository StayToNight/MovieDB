package com.staynight.moviedb

import android.app.Application
import com.staynight.moviedb.di.AppComponent
import com.staynight.moviedb.di.DaggerAppComponent

class MovieApp: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder().build()
    }
}