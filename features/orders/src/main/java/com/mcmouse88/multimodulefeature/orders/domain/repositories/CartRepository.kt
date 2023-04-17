package com.mcmouse88.multimodulefeature.orders.domain.repositories

import com.mcmouse88.multimodulefeature.orders.domain.entities.Cart

interface CartRepository {
    suspend fun getCart(): Cart
    suspend fun cleanUpCart()
}