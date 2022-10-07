package com.staynight.moviedb.presentation.ui.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.staynight.moviedb.domain.models.Movie
import com.staynight.moviedb.domain.models.Movies
import com.staynight.moviedb.domain.usecase.*
import com.staynight.moviedb.utils.helpers.DisposeBagViewModel
import com.staynight.moviedb.utils.helpers.Paginator
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val addToWatchlistUseCase: AddToWatchlistUseCase,
    private val getWatchlistUseCase: GetWatchlistUseCase
) : DisposeBagViewModel() {

    var topRatedState by mutableStateOf(State(title = "Top rated"))
    var popularState by mutableStateOf(State(title = "Popular"))
    var upcomingState by mutableStateOf(State(title = "Upcoming"))
    val paginatorTopRated = Paginator(
        initialKey = topRatedState.page,
        onLoadUpdated = { topRatedState = topRatedState.copy(isLoading = it) },
        onRequest = { getTopRatedMoviesUseCase.getTopRatedMovies(it) },
        getNextKey = { topRatedState.page + 1 },
        onError = { topRatedState = topRatedState.copy(error = it.toString()) },
        onSuccess = { items, newKey ->
            topRatedState = topRatedState.copy(
                movies = topRatedState.movies + (items.results ?: emptyList()),
                page = newKey,
                endReached = items.results.isNullOrEmpty()
            )
        }
    )
    val paginatorPopular = Paginator(
        initialKey = popularState.page,
        onLoadUpdated = { popularState = popularState.copy(isLoading = it) },
        onRequest = { getPopularMoviesUseCase.getPopularMovies(it) },
        getNextKey = { popularState.page + 1 },
        onError = { popularState = popularState.copy(error = it.toString()) },
        onSuccess = { items, newKey ->
            popularState = popularState.copy(
                movies = popularState.movies + (items.results ?: emptyList()),
                page = newKey,
                endReached = items.results.isNullOrEmpty()
            )
        }
    )
    val paginatorUpcoming = Paginator(
        initialKey = upcomingState.page,
        onLoadUpdated = { upcomingState = upcomingState.copy(isLoading = it) },
        onRequest = { getUpcomingMoviesUseCase.getUpcomingMovies(it) },
        getNextKey = { upcomingState.page + 1 },
        onError = { upcomingState = upcomingState.copy(error = it.toString()) },
        onSuccess = { items, newKey ->
            upcomingState = upcomingState.copy(
                movies = upcomingState.movies + (items.results ?: emptyList()),
                page = newKey,
                endReached = items.results.isNullOrEmpty()
            )
        }
    )

    init {
        loadNextItems(paginatorTopRated)
        loadNextItems(paginatorPopular)
        loadNextItems(paginatorUpcoming)
    }

    fun loadNextItems(paginator: Paginator<Int, Movies>) {
        disposeBag.add(Observable.just {
            paginator.loadNextItems()
        }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe {
                it.invoke()
            })
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

//    private fun getWatchList() {
//        disposeBag.add(getWatchlistUseCase.getWatchlist()
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeOn(Schedulers.io())
//            .subscribe(
//                { value ->
//                    categories.forEach { category ->
//                        category.movies.forEach { movie ->
//                            value.results?.forEach {
//                                if (movie.title == it.title) {
//                                    movie.atWatchlist = true
//                                }
//                            }
//                        }
//                    }
//                    state.value = State.Movies(categories)
//                },
//                { error -> Log.e("WATCHLIST", error.toString()) }
//            )
//        )
//    }

    data class State(
        val isLoading: Boolean = false,
        val movies: List<Movie> = emptyList(),
        val error: String? = null,
        val endReached: Boolean = false,
        val page: Int = 1,
        val title: String
    )
}
