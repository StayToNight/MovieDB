package com.staynight.moviedb.data.repository

import com.staynight.moviedb.data.models.*
import com.staynight.moviedb.data.network.Api
import com.staynight.moviedb.domain.repository.AuthRepository
import io.reactivex.Single
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val api: Api) : AuthRepository {
    override fun getRequestToken(): Single<RequestTokenData> = api.getRequestToken()
    override fun authWithLogin(
        login: String,
        password: String,
        requestToken: String
    ): Single<AuthWithLoginResponseData> =
        api.authWithLogin(AuthRequestBody(login, password, requestToken))

    override fun createNewSession(requestToken: String): Single<CreateNewSessionResponseData> = api.createSession(
        CreateNewSessionRequestBody(requestToken)
    )
}