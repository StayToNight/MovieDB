package com.staynight.moviedb.domain.usecase

import com.staynight.moviedb.domain.repository.AuthRepository
import javax.inject.Inject

class AuthWithLoginUseCase @Inject constructor(private val authRepository: AuthRepository) {
    fun authWithLogin(login: String, password: String, requestToken: String) =
        authRepository.authWithLogin(login, password, requestToken)
}