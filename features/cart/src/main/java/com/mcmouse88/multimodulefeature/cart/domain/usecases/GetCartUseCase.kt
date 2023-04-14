package com.mcmouse88.multimodulefeature.cart.domain.usecases

import com.mcmouse88.multimodulefeature.cart.domain.entities.Cart
import com.mcmouse88.multimodulefeature.cart.domain.entities.CartItem
import com.mcmouse88.multimodulefeature.cart.domain.factories.PriceFactory
import com.mcmouse88.multimodulefeature.cart.domain.repositories.CartRepository
import com.mcmouse88.multimodulefeature.core.common.Container
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCartUseCase @Inject constructor(
    private val cartRepository: CartRepository,
    private val priceFactory: PriceFactory
) {

    fun getCart(): Flow<Container<Cart>> {
        return cartRepository.getCartItems().map { container ->
            container.map {
                Cart(it, priceFactory)
            }
        }
    }

    suspend fun getCartById(cartItemId: Long): CartItem {
        return cartRepository.getCartItemById(cartItemId)
    }

    fun reload() {
        cartRepository.reload()
    }
}