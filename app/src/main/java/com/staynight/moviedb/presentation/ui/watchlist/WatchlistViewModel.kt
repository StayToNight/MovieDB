package com.staynight.moviedb.presentation.ui.watchlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.staynight.moviedb.domain.models.Movie
import com.staynight.moviedb.domain.usecase.AddToWatchlistUseCase
import com.staynight.moviedb.domain.usecase.GetWatchlistUseCase
import com.staynight.moviedb.utils.helpers.DisposeBagViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class WatchlistViewModel @Inject constructor(
    private val getWatchlistUseCase: GetWatchlistUseCase,
    private val addToWatchlistUseCase: AddToWatchlistUseCase
) : DisposeBagViewModel() {
    private val state = MutableLiveData<List<Movie>>()
    val liveData: LiveData<List<Movie>> = state

    fun getWatchList() {
        disposeBag.add(getWatchlistUseCase.getWatchlist()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { value ->
                    value.results?.forEach { it.atWatchlist = true }
                    state.value = value.results ?: listOf()
                },
                { error -> Log.e("WATCHLIST", error.toString()) }
            )
        )
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