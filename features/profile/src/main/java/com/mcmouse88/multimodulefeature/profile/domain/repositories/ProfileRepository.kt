package com.mcmouse88.multimodulefeature.profile.domain.repositories

import com.mcmouse88.multimodulefeature.core.common.Container
import com.mcmouse88.multimodulefeature.profile.domain.entities.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun getProfile(): Flow<Container<Profile>>
    fun reload()
    suspend fun editUsername(newUsername: String)
}