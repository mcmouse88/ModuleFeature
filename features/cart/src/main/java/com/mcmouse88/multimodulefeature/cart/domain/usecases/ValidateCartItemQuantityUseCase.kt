package com.mcmouse88.multimodulefeature.cart.domain.usecases

import com.mcmouse88.multimodulefeature.cart.domain.entities.CartItem
import com.mcmouse88.multimodulefeature.cart.domain.exceptions.QuantityOutOfRangeException
import com.mcmouse88.multimodulefeature.cart.domain.repositories.ProductRepository
import javax.inject.Inject

class ValidateCartItemQuantityUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {

    suspend fun validateNewQuantity(cartItem: CartItem, newQuantity: Int) {
        if (newQuantity > getMaxQuantity(cartItem)) throw QuantityOutOfRangeException()
        if (newQuantity < 1) throw QuantityOutOfRangeException()
    }

    suspend fun getMaxQuantity(cartItem: CartItem): Int {
        return productRepository.getAvailableQuantity(cartItem.product.id)
    }
}