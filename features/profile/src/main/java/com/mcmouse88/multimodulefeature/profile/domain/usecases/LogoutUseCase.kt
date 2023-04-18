package com.mcmouse88.multimodulefeature.profile.domain.usecases

import com.mcmouse88.multimodulefeature.profile.domain.repositories.AuthTokenRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authTokenRepository: AuthTokenRepository
) {
    suspend fun logout() {
        authTokenRepository.clearToken()
    }
}