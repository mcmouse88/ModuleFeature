package com.mcmouse88.multimodulefeature.cart.domain.usecases

import com.mcmouse88.multimodulefeature.cart.domain.entities.CartItem
import com.mcmouse88.multimodulefeature.cart.domain.repositories.CartRepository
import javax.inject.Inject

class DeleteCartItemUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend fun deleteCartItems(cartItems: List<CartItem>) {
        cartRepository.deleteCartItems(cartItems.map { it.id })
    }
}