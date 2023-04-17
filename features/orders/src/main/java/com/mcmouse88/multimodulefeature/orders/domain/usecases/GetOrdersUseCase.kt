package com.mcmouse88.multimodulefeature.orders.domain.usecases

import com.mcmouse88.multimodulefeature.core.common.Container
import com.mcmouse88.multimodulefeature.orders.domain.entities.Order
import com.mcmouse88.multimodulefeature.orders.domain.repositories.OrdersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOrdersUseCase @Inject constructor(
    private val ordersRepository: OrdersRepository
) {

    fun getOrders(): Flow<Container<List<Order>>> {
        return ordersRepository.getOrders()
    }

    fun reload() {
        ordersRepository.reload()
    }
}