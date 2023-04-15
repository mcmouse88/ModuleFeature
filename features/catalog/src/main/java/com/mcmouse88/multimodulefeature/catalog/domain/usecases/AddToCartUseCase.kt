package com.mcmouse88.multimodulefeature.catalog.domain.usecases

import com.mcmouse88.multimodulefeature.catalog.domain.entities.Product
import com.mcmouse88.multimodulefeature.catalog.domain.repositories.CartRepository
import com.mcmouse88.multimodulefeature.core.common.Container
import com.mcmouse88.multimodulefeature.core.common.Core
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend fun addToCart(product: Product) = withTimeout(Core.localTimeoutMillis) {
        val productIdsInCart = cartRepository.getProductIdentifiersInCart()
            .filterIsInstance<Container.Success<Set<Long>>>()
            .first()
        if (!productIdsInCart.value.contains(product.id)) {
            cartRepository.addToCart(product.id)
        }
    }
}