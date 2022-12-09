package com.staynight.moviedb.utils.helpers

import com.staynight.moviedb.domain.models.Movies
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class Paginator<Int, Movies>(
    private inline val initialKey: Int,
    private val onLoadUpdated: (Boolean) -> Unit,
    private val onRequest: (nextKey: Int) -> Single<Movies>,
    private val getNextKey: (Movies) -> Int,
    private val onError: (Throwable?) -> Unit,
    private val onSuccess: (movies: Movies, newKey: Int) -> Unit
): PaginatorInterface<Int, Movies> {
    private var currentKey = initialKey
    private var isMakingRequest = false
    private val disposeBag = CompositeDisposable()

    override fun loadNextItems() {
        if (isMakingRequest) {
            return
        }
        isMakingRequest = true
        onLoadUpdated(true)
        val result = onRequest(currentKey)
        isMakingRequest = false
        disposeBag.add(result
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { result ->
                    currentKey = getNextKey(result)
                    onSuccess(result, currentKey)
                    onLoadUpdated(false)
                },
                { error -> onError(throw Throwable(error)) }
            )
        )
    }

    override fun reset(){
        currentKey = initialKey
    }
}