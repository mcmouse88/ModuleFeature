package com.mcmouse88.multimodulefeature.orders.domain.entities

class CartItem(
    val name: String,
    val quantity: Int,
    val productId: Long,
    val price: Price
)