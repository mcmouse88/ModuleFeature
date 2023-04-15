package com.mcmouse88.multimodulefeature.catalog.domain.repositories

import com.mcmouse88.multimodulefeature.catalog.domain.entities.Price
import com.mcmouse88.multimodulefeature.catalog.domain.entities.Product
import com.mcmouse88.multimodulefeature.catalog.domain.entities.ProductFilter
import com.mcmouse88.multimodulefeature.core.common.Container
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun getProducts(filter: ProductFilter): Flow<Container<List<Product>>>

    suspend fun getProduct(id: Long): Product

    suspend fun getMinPossiblePrice(): Price

    suspend fun getMaxPossiblePrice(): Price

    suspend fun getAllCategories(): List<String>
}