package com.staynight.moviedb.domain.usecase

import com.staynight.moviedb.domain.repository.AuthRepository
import javax.inject.Inject

class GetRequestTokenUseCase @Inject constructor(private val authRepository: AuthRepository) {
    fun getRequestToken() = authRepository.getRequestToken()
}