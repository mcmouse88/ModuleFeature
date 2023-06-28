package com.mcmouse88.multimodulefeature.glue.orders.repositories

import com.mcmouse88.multimodulefeature.data.ProductsDataRepository
import com.mcmouse88.multimodulefeature.orders.domain.repositories.ProductsRepository
import javax.inject.Inject

class AdapterProductRepository @Inject constructor(
    private val productsDataRepository: ProductsDataRepository
) : ProductsRepository {

    override suspend fun changeQuantityBy(productId: Long, byValue: Int) {
        productsDataRepository.changeQuantityBy(productId, byValue)
    }
}