package com.mcmouse88.multimodulefeature.glue.cart.repositories

import com.mcmouse88.multimodulefeature.cart.domain.repositories.ProductRepository
import com.mcmouse88.multimodulefeature.data.ProductsDataRepository
import javax.inject.Inject

class AdapterProductRepository @Inject constructor(
    private val productsDataRepository: ProductsDataRepository
) : ProductRepository {
    override suspend fun getAvailableQuantity(productId: Long): Int {
        return productsDataRepository.getProductById(productId).quantityAvailable
    }
}