package com.staynight.moviedb.presentation.ui.watchlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.staynight.moviedb.data.models.Movie
import com.staynight.moviedb.domain.usecase.AddToWatchlistUseCase
import com.staynight.moviedb.domain.usecase.GetWatchlistUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class WatchlistViewModel @Inject constructor(
    private val getWatchlistUseCase: GetWatchlistUseCase,
    private val addToWatchlistUseCase: AddToWatchlistUseCase
) : ViewModel() {
    private val state = MutableLiveData<List<Movie>>()
    val liveData: LiveData<List<Movie>> = state

    fun getWatchList() {
        getWatchlistUseCase.getWatchlist()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { value ->
                    value.results?.forEach { it.atWatchlist = true }
                    state.value = value.results ?: listOf()
                },
                { error -> Log.e("WATCHLIST", error.toString()) }
            )
    }

    fun addToWatchList(mediaId: Int, mediaType: String, addToWatchlist: Boolean) {
        addToWatchlistUseCase.addToWatchList(mediaId, mediaType, addToWatchlist)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {},
                { error -> Log.e("DAGGERTEST", error.toString()) }
            )
    }
}