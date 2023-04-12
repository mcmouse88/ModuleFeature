package com.mcmouse88.multimodulefeature.navigation.domain.repositories

import com.mcmouse88.multimodulefeature.core.common.Container
import kotlinx.coroutines.flow.Flow

interface GetCartItemsCountsRepository {
    fun getCartItemCount(): Flow<Container<Int>>
}