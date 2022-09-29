package com.staynight.moviedb

import android.app.Application
import com.staynight.moviedb.di.AppComponent
import com.staynight.moviedb.di.AppModule
import com.staynight.moviedb.di.DaggerAppComponent

class MovieApp: Application() {

    var appComponent: AppComponent? = null

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}