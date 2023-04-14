package com.mcmouse88.multimodulefeature.cart.domain.usecases

import com.mcmouse88.multimodulefeature.cart.domain.entities.CartItem
import com.mcmouse88.multimodulefeature.cart.domain.repositories.CartRepository
import javax.inject.Inject

class ChangeCartItemQuantityUseCase @Inject constructor(
    private val cartRepository: CartRepository,
    private val validateQuantityUseCase: ValidateCartItemQuantityUseCase
) {

    suspend fun incrementQuantity(cartItem: CartItem) {
        val newQuantity = cartItem.quantity + 1
        changeQuantity(cartItem, newQuantity)
    }

    suspend fun decrementQuantity(cartItem: CartItem) {
        val newQuantity = cartItem.quantity - 1
        cartRepository.changeQuantity(cartItem.id, newQuantity)
    }

    suspend fun changeQuantity(cartItem: CartItem, newQuantity: Int) {
        validateNewQuantity(cartItem, newQuantity)
        cartRepository.changeQuantity(cartItem.id, newQuantity)
    }

    private suspend fun validateNewQuantity(cartItem: CartItem, newQuantity: Int) {
        validateQuantityUseCase.validateNewQuantity(cartItem, newQuantity)
    }
}