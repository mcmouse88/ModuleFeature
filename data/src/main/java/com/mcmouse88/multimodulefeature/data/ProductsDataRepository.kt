package com.mcmouse88.multimodulefeature.data

import com.mcmouse88.multimodulefeature.core.common.Container
import com.mcmouse88.multimodulefeature.data.products.entities.ProductDataEntity
import com.mcmouse88.multimodulefeature.data.products.entities.ProductDataFilter
import kotlinx.coroutines.flow.Flow

interface ProductsDataRepository {

    fun getProducts(filter: ProductDataFilter): Flow<Container<List<ProductDataEntity>>>

    suspend fun changeQuantityBy(id: Long, quantityBy: Int)

    suspend fun getProductById(id: Long): ProductDataEntity

    suspend fun getMinPriceUsdCents(): Int

    suspend fun getMaxPriceUsdCents(): Int

    suspend fun getDiscountPriceUsdCentsForEntity(product: ProductDataEntity): Int?

    suspend fun getAllCategories(): List<String>
}