package com.mcmouse88.multimodulefeature.catalog.domain.repositories

import com.mcmouse88.multimodulefeature.core.common.Container
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getProductIdentifiersInCart(): Flow<Container<Set<Long>>>
    fun reload()
    suspend fun addToCart(productId: Long)
}