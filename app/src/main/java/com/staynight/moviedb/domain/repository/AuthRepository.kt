package com.staynight.moviedb.domain.repository

import com.staynight.moviedb.data.models.RequestTokenData
import io.reactivex.Observable

interface AuthRepository {
    fun getRequestToken() : Observable<RequestTokenData>
}