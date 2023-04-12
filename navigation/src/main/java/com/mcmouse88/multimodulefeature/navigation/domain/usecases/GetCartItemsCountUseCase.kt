package com.mcmouse88.multimodulefeature.navigation.domain.usecases

import com.mcmouse88.multimodulefeature.core.common.Container
import com.mcmouse88.multimodulefeature.navigation.domain.repositories.GetCartItemsCountsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCartItemsCountUseCase @Inject constructor(
    private val getCartItemCountRepository: GetCartItemsCountsRepository
) {
    fun getCartItemCount(): Flow<Container<Int>> {
        return getCartItemCountRepository.getCartItemCount()
    }
}