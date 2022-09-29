package com.staynight.moviedb.presentation.ui.auth

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.staynight.moviedb.domain.usecase.AuthWithLoginUseCase
import com.staynight.moviedb.domain.usecase.CreateNewSessionUseCase
import com.staynight.moviedb.domain.usecase.GetRequestTokenUseCase
import com.staynight.moviedb.domain.usecase.SaveSessionIDUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val getRequestTokenUseCase: GetRequestTokenUseCase,
    private val authWithLoginUseCase: AuthWithLoginUseCase,
    private val createNewSessionUseCase: CreateNewSessionUseCase,
    private val saveSessionIDUseCase: SaveSessionIDUseCase
) :
    ViewModel() {
    private val state = MutableLiveData<State>()
    val liveData: LiveData<State> = state
    var requestToken = ""

    init {
        getRequestToken()
    }


    @SuppressLint("CheckResult")
    fun authWithLogin(login: String, password: String) {
        state.value = State.ShowLoading
        authWithLoginUseCase.authWithLogin(login, password, requestToken)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { value -> createNewSession(value.requestToken) },
                { error -> Log.e("AUTH auth", error.printStackTrace().toString()) }
            )
    }

    @SuppressLint("CheckResult")
    private fun createNewSession(requestToken: String) {
        createNewSessionUseCase.createNewSession(requestToken)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { value ->
                    Log.e("SESSION", value.sessionId )
                    saveSessionIDUseCase.saveSessionID(value.sessionId)
                    state.value = State.HideLoading
                    state.value = State.Success
                },
                { error -> Log.e("AUTH create", error.toString())
                    state.value = State.Error }
            )
    }

    @SuppressLint("CheckResult")
    private fun getRequestToken() {
        getRequestTokenUseCase.getRequestToken()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { value -> requestToken = value.requestToken },
                { error -> Log.e("AUTH token", error.toString())
                    state.value = State.Error }
            )
    }

    sealed class State {
        object Error : State()
        object Success : State()
        object ShowLoading : State()
        object HideLoading : State()
    }
}