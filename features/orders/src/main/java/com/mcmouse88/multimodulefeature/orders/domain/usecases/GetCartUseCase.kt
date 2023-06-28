package com.mcmouse88.multimodulefeature.orders.domain.usecases

import com.mcmouse88.multimodulefeature.orders.domain.entities.Cart
import com.mcmouse88.multimodulefeature.orders.domain.repositories.CartRepository
import javax.inject.Inject

class GetCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend fun getCart(): Cart {
        return cartRepository.getCart()
    }
}