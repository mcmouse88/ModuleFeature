package com.mcmouse88.multimodulefeature.orders.domain.usecases

import com.mcmouse88.multimodulefeature.orders.domain.entities.Order
import com.mcmouse88.multimodulefeature.orders.domain.entities.OrderStatus
import com.mcmouse88.multimodulefeature.orders.domain.exceptions.InvalidOrderStatusException
import com.mcmouse88.multimodulefeature.orders.domain.repositories.OrdersRepository
import javax.inject.Inject

class CancelOrderUseCase @Inject constructor(
    private val ordersRepository: OrdersRepository
) {

    suspend fun cancelOrder(order: Order) {
        if (canCancel(order).not()) throw InvalidOrderStatusException()
        ordersRepository.changeStatus(order.uuid, OrderStatus.CANCELLED)
    }

    fun canCancel(order: Order): Boolean {
        return order.status == OrderStatus.CREATED || order.status == OrderStatus.ACCEPTED
    }
}