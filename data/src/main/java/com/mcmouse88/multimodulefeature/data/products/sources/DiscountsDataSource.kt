package com.mcmouse88.multimodulefeature.data.products.sources

interface DiscountsDataSource {

    suspend fun getDiscountPercentage(productId: Long): Int?
}