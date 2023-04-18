package com.mcmouse88.multimodulefeature.profile.domain.usecases

import com.mcmouse88.multimodulefeature.core.common.Container
import com.mcmouse88.multimodulefeature.profile.domain.entities.Profile
import com.mcmouse88.multimodulefeature.profile.domain.repositories.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    fun getProfile(): Flow<Container<Profile>> {
        return profileRepository.getProfile()
    }

    fun reload() {
        profileRepository.reload()
    }
}