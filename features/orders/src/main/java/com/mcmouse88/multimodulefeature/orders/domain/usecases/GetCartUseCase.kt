package com.mcmouse88.multimodulefeature.orders.domain.usecases

import com.mcmouse88.multimodulefeature.orders.domain.entities.Cart
import com.mcmouse88.multimodulefeature.orders.domain.repositories.CartRepository

class GetCartUseCase(
    private val cartRepository: CartRepository
) {
    suspend fun getCart(): Cart {
        return cartRepository.getCart()
    }
}