package com.mcmouse88.multimodulefeature.data.orders

import com.mcmouse88.multimodulefeature.core.common.Container
import com.mcmouse88.multimodulefeature.core.common.flow.LazyFlowSubjectFactory
import com.mcmouse88.multimodulefeature.data.OrdersDataRepository
import com.mcmouse88.multimodulefeature.data.orders.entities.CreateOrderDataEntity
import com.mcmouse88.multimodulefeature.data.orders.entities.OrderDataEntity
import com.mcmouse88.multimodulefeature.data.orders.entities.OrderStatusDataValue
import com.mcmouse88.multimodulefeature.data.orders.sources.OrdersDataSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class RealOrdersDataRepository @Inject constructor(
    private val ordersDataSource: OrdersDataSource,
    lazyFlowSubjectFactory: LazyFlowSubjectFactory
) : OrdersDataRepository {

    private val ordersSubject = lazyFlowSubjectFactory.create {
        delay(1.seconds)
        ordersDataSource.getOrders()
    }

    override fun getOrders(): Flow<Container<List<OrderDataEntity>>> {
        return ordersSubject.listen()
    }

    override fun reload() {
        ordersSubject.newAsyncLoad()
    }

    override suspend fun changeStatus(orderUuid: String, status: OrderStatusDataValue) {
        ordersDataSource.setStatus(orderUuid, status)
        ordersSubject.newAsyncLoad(silently = true)
    }

    override suspend fun createOrder(data: CreateOrderDataEntity) {
        ordersDataSource.createOrder(data)
        ordersSubject.newAsyncLoad(silently = true)
    }
}