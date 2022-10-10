package com.staynight.moviedb.presentation.ui.watchlist

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.staynight.moviedb.domain.models.Movies
import com.staynight.moviedb.domain.usecase.AddToWatchlistUseCase
import com.staynight.moviedb.domain.usecase.GetWatchlistUseCase
import com.staynight.moviedb.presentation.ui.home.HomeViewModel
import com.staynight.moviedb.utils.helpers.DisposeBagViewModel
import com.staynight.moviedb.utils.helpers.Paginator
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val getWatchlistUseCase: GetWatchlistUseCase,
    private val addToWatchlistUseCase: AddToWatchlistUseCase
) : DisposeBagViewModel() {

    var state by mutableStateOf(HomeViewModel.State(title = ""))
    val paginator = Paginator(
        initialKey = state.page,
        onLoadUpdated = { state = state.copy(isLoading = it) },
        onRequest = { getWatchlistUseCase.getWatchlist(1) },
        getNextKey = { state.page + 1 },
        onError = { state = state.copy(error = it.toString()) },
        onSuccess = { items, newKey ->
            state = state.copy(
                movies = state.movies + (items.results ?: emptyList()),
                page = newKey,
                endReached = items.results.isNullOrEmpty()
            )
        }
    )

    init {
        loadNextItems(paginator)
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
        disposeBag.add(addToWatchlistUseCase.addToWatchList(mediaId, mediaType, addToWatchlist)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {},
                { error -> Log.e("DAGGERTEST", error.toString()) }
            )
        )
    }
}