package com.mcmouse88.multimodulefeature.catalog.domain.usecases

import com.mcmouse88.multimodulefeature.catalog.domain.entities.ProductWithCartInfo
import com.mcmouse88.multimodulefeature.catalog.domain.repositories.CartRepository
import com.mcmouse88.multimodulefeature.catalog.domain.repositories.ProductRepository
import com.mcmouse88.multimodulefeature.core.common.Container
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetProductDetailsUseCase @Inject constructor(
    private val productsRepository: ProductRepository,
    private val cartRepository: CartRepository
) {

    fun getProduct(id: Long): Flow<Container<ProductWithCartInfo>> {
        return cartRepository.getProductIdentifiersInCart()
            .map { container ->
                container.suspendMap { idsInCart ->
                    ProductWithCartInfo(
                        product = productsRepository.getProduct(id),
                        isInCart = idsInCart.contains(id)
                    )
                }
            }
    }

    fun reload() {
        cartRepository.reload()
    }
}