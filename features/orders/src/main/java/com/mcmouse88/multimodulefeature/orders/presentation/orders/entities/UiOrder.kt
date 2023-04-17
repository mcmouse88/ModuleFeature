package com.mcmouse88.multimodulefeature.orders.presentation.orders.entities

import com.mcmouse88.multimodulefeature.orders.domain.entities.Order
import com.mcmouse88.multimodulefeature.orders.domain.entities.OrderItem
import com.mcmouse88.multimodulefeature.orders.domain.entities.OrderStatus

data class UiOrder(
    val origin: Order,
    val canCancel: Boolean,
    val cancelInProgress: Boolean
) {
    val uuid: String get() = origin.uuid
    val createdAt: String get() = origin.createdAt
    val recipient: String get() = origin.orderDeliverInfo
    val orderItems: List<OrderItem> get() = origin.orderItems
    val status: OrderStatus get() = origin.status
}