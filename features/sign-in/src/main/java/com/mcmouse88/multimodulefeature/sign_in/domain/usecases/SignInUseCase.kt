package com.mcmouse88.multimodulefeature.sign_in.domain.usecases

import com.mcmouse88.multimodulefeature.sign_in.domain.exceptions.EmptyEmailException
import com.mcmouse88.multimodulefeature.sign_in.domain.exceptions.EmptyPasswordException
import com.mcmouse88.multimodulefeature.sign_in.domain.repositories.AuthServiceRepository
import com.mcmouse88.multimodulefeature.sign_in.domain.repositories.AuthTokenRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authTokenRepository: AuthTokenRepository,
    private val authServiceRepository: AuthServiceRepository
) {
    suspend fun signIn(email: String, password: String) {
        if (email.isBlank()) throw EmptyEmailException()
        if (password.isBlank()) throw EmptyPasswordException()
        val token = authServiceRepository.signIn(email, password)
        authTokenRepository.setToken(token)
    }
}