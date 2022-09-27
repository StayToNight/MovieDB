package com.staynight.moviedb.data.repository

import com.staynight.moviedb.data.models.RequestTokenData
import com.staynight.moviedb.data.network.Api
import com.staynight.moviedb.domain.repository.AuthRepository
import io.reactivex.Observable
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val api: Api): AuthRepository {
    override fun getRequestToken(): Observable<RequestTokenData> = api.getRequestToken()
}