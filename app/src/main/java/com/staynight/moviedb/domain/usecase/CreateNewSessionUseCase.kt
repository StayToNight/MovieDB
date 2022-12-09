package com.staynight.moviedb.domain.usecase

import com.staynight.moviedb.domain.repository.AuthRepository
import javax.inject.Inject

class CreateNewSessionUseCase @Inject constructor(private val authRepository: AuthRepository) {
    fun createNewSession(requestToken: String) = authRepository.createNewSession(requestToken)
}