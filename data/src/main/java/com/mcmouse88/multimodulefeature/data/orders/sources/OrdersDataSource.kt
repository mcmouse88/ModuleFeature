package com.mcmouse88.multimodulefeature.data.orders.sources

import com.mcmouse88.multimodulefeature.data.orders.entities.CreateOrderDataEntity
import com.mcmouse88.multimodulefeature.data.orders.entities.OrderDataEntity
import com.mcmouse88.multimodulefeature.data.orders.entities.OrderStatusDataValue

interface OrdersDataSource {

    suspend fun getOrders(): List<OrderDataEntity>

    suspend fun setStatus(uuid: String, newStatus: OrderStatusDataValue)

    suspend fun createOrder(createOrderDataEntity: CreateOrderDataEntity)
}