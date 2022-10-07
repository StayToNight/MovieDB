package com.staynight.moviedb.presentation.ui.auth

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.staynight.moviedb.domain.usecase.AuthWithLoginUseCase
import com.staynight.moviedb.domain.usecase.CreateNewSessionUseCase
import com.staynight.moviedb.domain.usecase.GetRequestTokenUseCase
import com.staynight.moviedb.domain.usecase.SaveSessionIDUseCase
import com.staynight.moviedb.utils.helpers.DisposeBagViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val getRequestTokenUseCase: GetRequestTokenUseCase,
    private val authWithLoginUseCase: AuthWithLoginUseCase,
    private val createNewSessionUseCase: CreateNewSessionUseCase,
    private val saveSessionIDUseCase: SaveSessionIDUseCase
) :
    DisposeBagViewModel() {
    private val state = MutableLiveData<State>()
    val liveData: LiveData<State> = state
    var requestToken = ""
    var buttonLoadingState by mutableStateOf(false)

    init {
        getRequestToken()
    }


    fun authWithLogin(login: String, password: String) {
        buttonLoadingState = true
        disposeBag.add(authWithLoginUseCase.authWithLogin(login, password, requestToken)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { value -> createNewSession(value.requestToken) },
                { error -> Log.e("AUTH auth", error.printStackTrace().toString()) }
            )
        )
    }

    private fun createNewSession(requestToken: String) {
        disposeBag.add(createNewSessionUseCase.createNewSession(requestToken)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { value ->
                    Log.e("SESSION", value.sessionId)
                    saveSessionIDUseCase.saveSessionID(value.sessionId)
                    buttonLoadingState = false
                    state.value = State.Success
                },
                { error ->
                    Log.e("AUTH create", error.toString())
                    buttonLoadingState = false
                }
            )
        )
    }

    private fun getRequestToken() {
        disposeBag.add(getRequestTokenUseCase.getRequestToken()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { value -> requestToken = value.requestToken },
                { error ->
                    Log.e("AUTH token", error.toString())
                    buttonLoadingState = false
                }
            )
        )
    }

    sealed class State {
        object Success : State()
    }
}