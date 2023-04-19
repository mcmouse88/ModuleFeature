package com.mcmouse88.multimodulefeature.sign_in.domain.repositories

interface AuthTokenRepository {
    suspend fun setToken(token: String?)
    suspend fun getToken(): String?
}