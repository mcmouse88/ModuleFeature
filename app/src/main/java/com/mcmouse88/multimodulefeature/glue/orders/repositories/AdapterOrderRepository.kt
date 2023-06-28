package com.mcmouse88.multimodulefeature.glue.orders.repositories

import com.mcmouse88.multimodulefeature.core.common.Container
import com.mcmouse88.multimodulefeature.data.OrdersDataRepository
import com.mcmouse88.multimodulefeature.data.orders.entities.CreateOrderDataEntity
import com.mcmouse88.multimodulefeature.data.orders.entities.CreateOrderItemDataEntity
import com.mcmouse88.multimodulefeature.glue.orders.entities.OrderUsdPrice
import com.mcmouse88.multimodulefeature.glue.orders.mappers.OrderMapper
import com.mcmouse88.multimodulefeature.glue.orders.mappers.OrderStatusMapper
import com.mcmouse88.multimodulefeature.glue.orders.mappers.RecipientMapper
import com.mcmouse88.multimodulefeature.orders.domain.entities.Cart
import com.mcmouse88.multimodulefeature.orders.domain.entities.Order
import com.mcmouse88.multimodulefeature.orders.domain.entities.OrderStatus
import com.mcmouse88.multimodulefeature.orders.domain.entities.Recipient
import com.mcmouse88.multimodulefeature.orders.domain.repositories.OrdersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AdapterOrderRepository @Inject constructor(
    private val ordersDataRepository: OrdersDataRepository,
    private val orderStatusMapper: OrderStatusMapper,
    private val orderMapper: OrderMapper,
    private val recipientMapper: RecipientMapper
) : OrdersRepository {

    override suspend fun makeOrder(cart: Cart, recipient: Recipient) {
        ordersDataRepository.createOrder(
            CreateOrderDataEntity(recipientDataEntity = recipientMapper.toRecipientDataEntity(
                recipient
            ),
                items = cart.cartItems.map {
                    CreateOrderItemDataEntity(
                        productId = it.productId,
                        quantity = it.quantity,
                        priceUsdCents = (it.price as OrderUsdPrice).usdCents
                    )
                })
        )
    }

    override suspend fun changeStatus(orderUuid: String, status: OrderStatus) {
        ordersDataRepository.changeStatus(orderUuid, orderStatusMapper.toOrderStatusDataValue(status))
    }

    override fun getOrders(): Flow<Container<List<Order>>> {
        return ordersDataRepository.getOrders().map { container ->
            container.map { list ->
                list.map { orderDataEntity ->
                    orderMapper.toOrder(orderDataEntity)
                }
            }
        }
    }

    override fun reload() {
        ordersDataRepository.reload()
    }
}