package com.staynight.moviedb.di

import com.staynight.moviedb.presentation.ui.auth.AuthViewModel
import com.staynight.moviedb.presentation.ui.home.HomeViewModel
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

//    @Component.Builder
//    interface Builder {
//        fun build(): AppComponent
//    }

    fun getHomeViewModel(): HomeViewModel
    fun getAuthViewModel(): AuthViewModel


    // -------------------
//    fun injectHomeFragment(homeFragment: () -> Unit)
//    fun injectAuthFragment(authFragment: AuthFragmentCompose)
//    fun injectWatchlistFragment(watchlistFragment: WatchlistFragmentCompose)
}