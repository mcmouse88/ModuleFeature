package com.mcmouse88.multimodulefeature.sign_up.domain.usecases

import com.mcmouse88.multimodulefeature.sign_up.domain.entities.SignUpData
import com.mcmouse88.multimodulefeature.sign_up.domain.entities.SignUpField
import com.mcmouse88.multimodulefeature.sign_up.domain.exceptions.EmptyFieldException
import com.mcmouse88.multimodulefeature.sign_up.domain.repositories.SignUpRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val signUpRepository: SignUpRepository
) {
    suspend fun signUp(signUpData: SignUpData) {
        if (signUpData.email.isBlank()) throw EmptyFieldException(SignUpField.EMAIL)
        if (signUpData.username.isBlank()) throw EmptyFieldException(SignUpField.USERNAME)
        if (signUpData.password.isBlank()) throw EmptyFieldException(SignUpField.PASSWORD)
        if (signUpData.repeatPassword.isBlank()) throw EmptyFieldException(SignUpField.REPEAT_PASSWORD)
        signUpRepository.signUp(signUpData)
    }
}