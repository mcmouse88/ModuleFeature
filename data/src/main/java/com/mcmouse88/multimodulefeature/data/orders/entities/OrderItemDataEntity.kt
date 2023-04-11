package com.mcmouse88.multimodulefeature.data.orders.entities

data class OrderItemDataEntity(
    val id: String,
    val productName: String,
    val quantity: Int,
    val priceUsdCents: Int
)