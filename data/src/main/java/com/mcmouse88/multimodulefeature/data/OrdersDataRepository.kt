package com.mcmouse88.multimodulefeature.data

import com.mcmouse88.multimodulefeature.core.common.Container
import com.mcmouse88.multimodulefeature.data.orders.entities.CreateOrderDataEntity
import com.mcmouse88.multimodulefeature.data.orders.entities.OrderDataEntity
import com.mcmouse88.multimodulefeature.data.orders.entities.OrderStatusDataValue
import kotlinx.coroutines.flow.Flow

interface OrdersDataRepository {

    fun getOrders(): Flow<Container<List<OrderDataEntity>>>

    fun reload()

    suspend fun changeStatus(orderUuid: String, status: OrderStatusDataValue)

    suspend fun createOrder(data: CreateOrderDataEntity)
}