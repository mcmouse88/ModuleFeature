package com.mcmouse88.multimodulefeature.profile.domain.repositories

interface AuthTokenRepository {
    suspend fun clearToken()
}