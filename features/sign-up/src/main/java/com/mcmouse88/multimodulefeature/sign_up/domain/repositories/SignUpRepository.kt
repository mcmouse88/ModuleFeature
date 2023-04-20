package com.mcmouse88.multimodulefeature.sign_up.domain.repositories

import com.mcmouse88.multimodulefeature.sign_up.domain.entities.SignUpData

interface SignUpRepository {
    suspend fun signUp(signUpData: SignUpData)
}