package com.staynight.moviedb.di

import com.staynight.moviedb.presentation.ui.auth.AuthFragment
import com.staynight.moviedb.presentation.ui.home.HomeFragment
import com.staynight.moviedb.presentation.ui.watchlist.WatchlistFragment
import com.staynight.moviedb.utils.helpers.ViewModelBuilder
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    NetworkModule::class,
    RepositoryModule::class,
    ViewModelModule::class,
    ViewModelBuilder::class,
    StorageModule::class])
interface AppComponent {
    fun injectHomeFragment(homeFragment: HomeFragment)
    fun injectAuthFragment(authFragment: AuthFragment)
    fun injectWatchlistFragment(watchlistFragment: WatchlistFragment)
}