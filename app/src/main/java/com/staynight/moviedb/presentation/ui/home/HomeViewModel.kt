package com.staynight.moviedb.presentation.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.staynight.moviedb.data.models.Movie
import com.staynight.moviedb.domain.usecase.GetTopRatedMoviesUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase): ViewModel() {
    private val state = MutableLiveData<State>()
    val liveData: LiveData<State> = state

    fun getTopRatedMovies(){
        getTopRatedMoviesUseCase.getTopRatedMovies()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {value -> state.value = State.Movies(value.results)},
                {error -> Log.e("DAGGERTEST", error.toString(), )}
            )
    }


    sealed class State{
        data class Movies(val movies: List<Movie>): State()
    }
}