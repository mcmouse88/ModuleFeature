package com.mcmouse88.multimodulefeature.sign_in.domain.repositories

interface ProfileRepository {
    suspend fun canLoadProfile(): Boolean
}