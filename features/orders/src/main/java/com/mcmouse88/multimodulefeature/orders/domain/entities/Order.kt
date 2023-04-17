package com.mcmouse88.multimodulefeature.orders.domain.entities

data class Order(
    val uuid: String,
    val status: OrderStatus,
    val orderDeliverInfo: String,
    val createdAt: String,
    val orderItems: List<OrderItem>
)