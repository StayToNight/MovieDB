package com.staynight.moviedb.di

import androidx.lifecycle.ViewModel
import com.staynight.moviedb.presentation.ui.home.HomeViewModel
import com.staynight.moviedb.presentation.ui.main.MainViewModel
import com.staynight.moviedb.utils.helpers.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewMode: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewMode: HomeViewModel): ViewModel
}