package com.staynight.moviedb.domain.repository

import com.staynight.moviedb.data.models.AuthWithLoginResponseData
import com.staynight.moviedb.data.models.CreateNewSessionResponseData
import com.staynight.moviedb.data.models.RequestTokenData
import io.reactivex.Single

interface AuthRepository {
    fun getRequestToken(): Single<RequestTokenData>
    fun authWithLogin(
        login: String,
        password: String,
        requestToken: String
    ): Single<AuthWithLoginResponseData>
    fun createNewSession(requestToken: String): Single<CreateNewSessionResponseData>
}