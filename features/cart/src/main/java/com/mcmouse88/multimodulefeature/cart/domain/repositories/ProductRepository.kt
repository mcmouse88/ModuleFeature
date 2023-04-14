package com.mcmouse88.multimodulefeature.cart.domain.repositories

interface ProductRepository {
    suspend fun getAvailableQuantity(productId: Long): Int
}