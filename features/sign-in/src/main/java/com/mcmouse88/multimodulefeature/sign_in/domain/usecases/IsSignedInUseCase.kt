package com.mcmouse88.multimodulefeature.sign_in.domain.usecases

import com.mcmouse88.multimodulefeature.sign_in.domain.repositories.AuthTokenRepository
import com.mcmouse88.multimodulefeature.sign_in.domain.repositories.ProfileRepository
import javax.inject.Inject

class IsSignedInUseCase @Inject constructor(
    private val authTokenRepository: AuthTokenRepository,
    private val profileRepository: ProfileRepository
) {
    suspend fun isSignedIn(): Boolean {
        return authTokenRepository.getToken() != null && profileRepository.canLoadProfile()
    }
}