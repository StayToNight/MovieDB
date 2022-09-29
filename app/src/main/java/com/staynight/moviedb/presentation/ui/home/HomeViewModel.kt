package com.staynight.moviedb.presentation.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.staynight.moviedb.domain.usecase.AddToWatchlistUseCase
import com.staynight.moviedb.domain.usecase.GetPopularMoviesUseCase
import com.staynight.moviedb.domain.usecase.GetTopRatedMoviesUseCase
import com.staynight.moviedb.domain.usecase.GetUpcomingMoviesUseCase
import com.staynight.moviedb.presentation.ui.models.MovieGroup
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val addToWatchlistUseCase: AddToWatchlistUseCase
) : ViewModel() {
    private val state = MutableLiveData<State>()
    val liveData: LiveData<State> = state
    private val categories = mutableListOf<MovieGroup>()

    fun addToWatchList(mediaId: Int, mediaType: String, addToWatchlist: Boolean){
        addToWatchlistUseCase.addToWatchList(mediaId, mediaType, addToWatchlist)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {},
                {error -> Log.e("DAGGERTEST", error.toString())}
            )
    }

    fun getTopRatedMovies() {
        getTopRatedMoviesUseCase.getTopRatedMovies()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { value ->
                    if (!value.results.isNullOrEmpty()){
                        categories.add(MovieGroup("Top Rated", value.results))
                    }
                    getPopularMovies()
                },
                { error -> Log.e("DAGGERTEST", error.toString()) }
            )
    }

    private fun getPopularMovies() {
        getPopularMoviesUseCase.getPopularMovies()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { value ->
                    if (!value.results.isNullOrEmpty()){
                        categories.add(MovieGroup("Most Popular", value.results))
                    }
                    getUpcomingMovies()
                },
                { error -> Log.e("DAGGERTEST", error.toString()) }
            )
    }

    private fun getUpcomingMovies() {
        getUpcomingMoviesUseCase.getUpcomingMovies()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { value ->
                    if (!value.results.isNullOrEmpty()){
                        categories.add(MovieGroup("Upcoming", value.results))
                    }
                    state.value = State.Movies(categories)
                },
                { error -> Log.e("DAGGERTEST", error.toString()) }
            )
    }


    sealed class State {
        data class Movies(val movies: List<MovieGroup>) : State()
    }
}