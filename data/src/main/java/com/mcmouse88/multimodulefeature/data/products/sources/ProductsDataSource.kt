package com.mcmouse88.multimodulefeature.data.products.sources

import com.mcmouse88.multimodulefeature.data.products.entities.ProductDataEntity
import com.mcmouse88.multimodulefeature.data.products.entities.ProductDataFilter

interface ProductsDataSource {

    suspend fun getProducts(filter: ProductDataFilter): List<ProductDataEntity>

    suspend fun getProductById(id: Long): ProductDataEntity

    suspend fun getAllCategories(): List<String>

    suspend fun getDiscountPriceUsdCentsForEntity(product: ProductDataEntity): Int?

    suspend fun changeQuantityBy(id: Long, quantityBy: Int)
}