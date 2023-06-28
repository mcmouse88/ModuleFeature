package com.mcmouse88.multimodulefeature.glue.catalog.repositories

import com.mcmouse88.multimodulefeature.catalog.domain.repositories.CartRepository
import com.mcmouse88.multimodulefeature.core.common.Container
import com.mcmouse88.multimodulefeature.data.CartDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AdapterCartRepository @Inject constructor(
    private val cartDataRepository: CartDataRepository
) : CartRepository {

    override fun getProductIdentifiersInCart(): Flow<Container<Set<Long>>> {
        return cartDataRepository.getCart().map { container ->
            container.map { list ->
                list.map { it.productId }.toSet()
            }
        }
    }

    override fun reload() {
        cartDataRepository.reload()
    }

    override suspend fun addToCart(productId: Long) {
        cartDataRepository.addToCart(productId = productId, quantity = 1)
    }
}