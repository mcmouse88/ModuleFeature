package com.mcmouse88.multimodulefeature.navigation.domain.usecases

import com.mcmouse88.multimodulefeature.core.common.Container
import com.mcmouse88.multimodulefeature.navigation.domain.repositories.GetCurrentUsernameRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentUsernameUseCase @Inject constructor(
    private val getCurrentUsernameRepository: GetCurrentUsernameRepository
) {
    fun getUsername(): Flow<Container<String>> {
        return getCurrentUsernameRepository.getCurrentUsername()
    }
}