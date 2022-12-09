package com.staynight.moviedb.domain.usecase

import com.staynight.moviedb.data.storage.SessionManager
import javax.inject.Inject

class SaveSessionIDUseCase @Inject constructor(private val sessionManager: SessionManager) {
    fun saveSessionID(sessionID: String) {
        sessionManager.saveSessionID(sessionID)
    }
}