package com.mcmouse88.multimodulefeature.orders.domain.entities

data class OrderItem(
    val id: String,
    val name: String,
    val quantity: Int,
    val price: Price
)