package com.staynight.moviedb.di

import com.staynight.moviedb.presentation.ui.home.HomeFragment
import com.staynight.moviedb.presentation.ui.main.MainActivity
import com.staynight.moviedb.utils.helpers.ViewModelBuilder
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, RepositoryModule::class, ViewModelModule::class, ViewModelBuilder::class])
interface AppComponent {
    fun testInject(mainActivity: MainActivity)

    fun injectHomeFragment(homeFragment: HomeFragment)
}