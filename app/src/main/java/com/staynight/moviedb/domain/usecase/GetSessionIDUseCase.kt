package com.staynight.moviedb.domain.usecase

import com.staynight.moviedb.data.storage.SessionManager
import javax.inject.Inject

class GetSessionIDUseCase @Inject constructor(private val sessionManager: SessionManager) {
    fun getSessionID() =
        sessionManager.getSessionID()

}