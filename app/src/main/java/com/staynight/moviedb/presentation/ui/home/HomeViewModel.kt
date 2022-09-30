package com.staynight.moviedb.presentation.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.staynight.moviedb.data.models.Movie
import com.staynight.moviedb.data.models.MoviesData
import com.staynight.moviedb.domain.usecase.*
import com.staynight.moviedb.presentation.ui.models.MovieGroup
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val addToWatchlistUseCase: AddToWatchlistUseCase,
    private val getWatchlistUseCase: GetWatchlistUseCase
) : ViewModel() {
    private val state = MutableLiveData<State>()
    val liveData: LiveData<State> = state
    private val categories = mutableListOf<MovieGroup>()
    private val disposeBag = CompositeDisposable()
    private var topPage = 1
    private var popPage = 1
    private var upPage = 1


    override fun onCleared() {
        super.onCleared()
        disposeBag.dispose()
    }

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

    fun getAll() {
        val all = Single.zip(
            getTopRatedMovies(),
            getPopularMovies(),
            getUpcomingMovies(),
            Function3<MoviesData, MoviesData, MoviesData, List<MovieGroup>> { t1, t2, t3 ->
                return@Function3 listOf(
                    MovieGroup("Top Rated", t1.results?.toMutableList() ?: mutableListOf()),
                    MovieGroup("Popular", t2.results?.toMutableList() ?: mutableListOf()),
                    MovieGroup("Upcoming", t3.results?.toMutableList() ?: mutableListOf())
                )
            }
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { value ->
                categories.addAll(value)
                getWatchList()
            }
        disposeBag.add(all)
    }

    private fun getWatchList() {
        val watchlist = getWatchlistUseCase.getWatchlist()
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

        disposeBag.add(watchlist)
    }

    private fun getTopRatedMovies(): Single<MoviesData> {
        return getTopRatedMoviesUseCase.getTopRatedMovies()
            .subscribeOn(Schedulers.io())
            .onErrorReturn { return@onErrorReturn MoviesData(null, null, null, null) }
    }

    private fun getPopularMovies(): Single<MoviesData> {
        return getPopularMoviesUseCase.getPopularMovies()
            .subscribeOn(Schedulers.io())
            .onErrorReturn { return@onErrorReturn MoviesData(null, null, null, null) }
    }

    private fun getUpcomingMovies(): Single<MoviesData> {
        return getUpcomingMoviesUseCase.getUpcomingMovies()
            .subscribeOn(Schedulers.io())
            .onErrorReturn { return@onErrorReturn MoviesData(null, null, null, null) }
    }

    fun getTopRatedMovies(id: Int) {
        disposeBag.add(getTopRatedMoviesUseCase.getTopRatedMovies(++topPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn { return@onErrorReturn MoviesData(null, null, null, null) }
            .subscribe({ value ->
                state.value = State.NewMovies(value.results ?: emptyList(), id)
            }, {

            })
        )
    }

    fun getPopularMovies(id: Int) {
        disposeBag.add(getPopularMoviesUseCase.getPopularMovies(++popPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn { return@onErrorReturn MoviesData(null, null, null, null) }
            .subscribe({ value ->
                state.value = State.NewMovies(value.results ?: emptyList(), id)
            }, {

            })
        )
    }

    fun getUpcomingMovies(id: Int) {
        disposeBag.add(getUpcomingMoviesUseCase.getUpcomingMovies(++upPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn { return@onErrorReturn MoviesData(null, null, null, null) }
            .subscribe({ value ->
                state.value = State.NewMovies(value.results ?: emptyList(), id)
            }, {

            })
        )
    }

    sealed class State {
        data class Movies(val movies: List<MovieGroup>) : State()
        data class NewMovies(val movies: List<Movie>, val id: Int) : State()
    }
}