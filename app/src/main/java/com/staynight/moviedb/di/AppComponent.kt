package com.staynight.moviedb.di

import com.staynight.moviedb.presentation.ui.auth.AuthFragmentCompose
import com.staynight.moviedb.presentation.ui.home.HomeFragmentCompose
import com.staynight.moviedb.presentation.ui.watchlist.WatchlistFragmentCompose
import com.staynight.moviedb.utils.helpers.ViewModelBuilder
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        ViewModelModule::class,
        ViewModelBuilder::class,
        StorageModule::class,
        MapperModule::class]
)
interface AppComponent {
    fun injectHomeFragment(homeFragment: HomeFragmentCompose)
    fun injectAuthFragment(authFragment: AuthFragmentCompose)
    fun injectWatchlistFragment(watchlistFragment: WatchlistFragmentCompose)
}