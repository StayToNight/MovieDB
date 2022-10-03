package com.staynight.moviedb.presentation.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.staynight.moviedb.domain.models.Movie
import com.staynight.moviedb.domain.usecase.*
import com.staynight.moviedb.presentation.ui.models.MovieGroup
import com.staynight.moviedb.utils.helpers.DisposeBagViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val addToWatchlistUseCase: AddToWatchlistUseCase,
    private val getWatchlistUseCase: GetWatchlistUseCase,
    private val getHomePageUseCase: GetHomePageUseCase
) : DisposeBagViewModel() {
    private val state = MutableLiveData<State>()
    val liveData: LiveData<State> = state
    private val categories = mutableListOf<MovieGroup>()
    private var topPage = 1
    private var popPage = 1
    private var upPage = 1

    fun addToWatchList(mediaId: Int, mediaType: String, addToWatchlist: Boolean) {
        val add = addToWatchlistUseCase.addToWatchList(mediaId, mediaType, addToWatchlist)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {},
                { error -> Log.e("DAGGERTEST", error.toString()) }
            )
        disposeBag.add(add)
    }

    fun getHomePage() {
        disposeBag.add(
            getHomePageUseCase.getHomePage()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { value ->
                    categories.addAll(value)
                    getWatchList()
                }
        )
    }

    fun getTopRatedMovies(id: Int) {
        disposeBag.add(getTopRatedMoviesUseCase.getTopRatedMovies(++topPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ value ->
                state.value = State.NewMovies(value.results ?: emptyList(), id)
            }, {})
        )
    }

    fun getPopularMovies(id: Int) {
        disposeBag.add(getPopularMoviesUseCase.getPopularMovies(++popPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ value ->
                state.value = State.NewMovies(value.results ?: emptyList(), id)
            }, {})
        )
    }

    fun getUpcomingMovies(id: Int) {
        disposeBag.add(getUpcomingMoviesUseCase.getUpcomingMovies(++upPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ value ->
                state.value = State.NewMovies(value.results ?: emptyList(), id)
            }, {})
        )
    }

    private fun getWatchList() {
        disposeBag.add(getWatchlistUseCase.getWatchlist()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { value ->
                    categories.forEach { category ->
                        category.movies.forEach { movie ->
                            value.results?.forEach {
                                if (movie.title == it.title) {
                                    movie.atWatchlist = true
                                }
                            }
                        }
                    }
                    state.value = State.Movies(categories)
                },
                { error -> Log.e("WATCHLIST", error.toString()) }
            )
        )
    }

    sealed class State {
        data class Movies(val movies: List<MovieGroup>) : State()
        data class NewMovies(val movies: List<Movie>, val id: Int) : State()
    }
}