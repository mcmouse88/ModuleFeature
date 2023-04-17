package com.mcmouse88.multimodulefeature.orders.domain.repositories

import com.mcmouse88.multimodulefeature.core.common.Container
import com.mcmouse88.multimodulefeature.orders.domain.entities.Cart
import com.mcmouse88.multimodulefeature.orders.domain.entities.Order
import com.mcmouse88.multimodulefeature.orders.domain.entities.OrderStatus
import com.mcmouse88.multimodulefeature.orders.domain.entities.Recipient
import kotlinx.coroutines.flow.Flow

interface OrdersRepository {
    suspend fun makeOrder(cart: Cart, recipient: Recipient)
    suspend fun changeStatus(orderUuid: String, status: OrderStatus)
    fun getOrders(): Flow<Container<List<Order>>>
    fun reload()
}