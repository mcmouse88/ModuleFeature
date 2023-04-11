package com.mcmouse88.multimodulefeature.data.orders.entities

data class CreateOrderItemDataEntity(
    val productId: Long,
    val quantity: Int,
    val priceUsdCents: Int
)