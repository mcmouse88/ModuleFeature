package com.mcmouse88.multimodulefeature.sign_in.domain.repositories

interface AuthServiceRepository {
    suspend fun signIn(email: String, password: String): String
}