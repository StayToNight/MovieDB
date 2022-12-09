package com.staynight.moviedb.utils.helpers

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class DisposeBagViewModel: ViewModel() {
    val disposeBag = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposeBag.dispose()
    }
}