package com.mcmouse88.multimodulefeature.data.products.sources

import com.mcmouse88.multimodulefeature.data.products.entities.DiscountDataEntity
import javax.inject.Inject
import kotlin.random.Random

class InMemoryDiscountsDataSource @Inject constructor() : DiscountsDataSource {

    private val discounts = mutableListOf<DiscountDataEntity>()

    init {
        for (i in 1..50 step 3) {
            discounts.add(DiscountDataEntity(i.toLong(), Random.nextInt(10, 30)))
        }
    }

    override suspend fun getDiscountPercentage(productId: Long): Int? {
        return discounts.firstOrNull { it.productId == productId }?.discountPercentage
    }
}