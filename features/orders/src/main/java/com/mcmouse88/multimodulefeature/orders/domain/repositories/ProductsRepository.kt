package com.mcmouse88.multimodulefeature.orders.domain.repositories

interface ProductsRepository {
    suspend fun changeQuantityBy(productId: Long, byValue: Int)
}