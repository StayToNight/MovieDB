package com.staynight.moviedb.presentation.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.staynight.moviedb.domain.usecase.GetRequestTokenUseCase
import com.staynight.moviedb.utils.helpers.DisposeBagViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(private val getRequestTokenUseCase: GetRequestTokenUseCase) :
    DisposeBagViewModel() {
    private val state = MutableLiveData<String>()
    val liveData: LiveData<String> = state

    fun getRequestToken() {
        disposeBag.add(getRequestTokenUseCase.getRequestToken()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { value -> state.value = value.requestToken },
                { error -> Log.e("DAGGER", error.toString()) }
            )
        )
    }
}