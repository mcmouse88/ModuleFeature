package com.mcmouse88.multimodulefeature.profile.domain.usecases

import com.mcmouse88.multimodulefeature.profile.domain.exceptions.EmptyUsernameException
import com.mcmouse88.multimodulefeature.profile.domain.repositories.ProfileRepository
import javax.inject.Inject

class EditUserNameUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend fun editUsername(newUsername: String) {
        if (newUsername.isBlank()) throw EmptyUsernameException()
        profileRepository.editUsername(newUsername)
    }
}