package com.mcmouse88.multimodulefeature.glue.orders.mappers

import com.mcmouse88.multimodulefeature.data.orders.entities.OrderDataEntity
import com.mcmouse88.multimodulefeature.data.orders.entities.RecipientDataEntity
import com.mcmouse88.multimodulefeature.formatters.DataTimeFormatter
import com.mcmouse88.multimodulefeature.formatters.PriceFormatter
import com.mcmouse88.multimodulefeature.glue.orders.entities.OrderUsdPrice
import com.mcmouse88.multimodulefeature.orders.domain.entities.Order
import com.mcmouse88.multimodulefeature.orders.domain.entities.OrderItem
import javax.inject.Inject

class OrderMapper @Inject constructor(
    private val orderStatusMapper: OrderStatusMapper,
    private val priceFormatter: PriceFormatter,
    private val dateTimeFormatter: DataTimeFormatter
) {

    fun toOrder(orderDataEntity: OrderDataEntity): Order {
        return Order(
            uuid = orderDataEntity.uuid,
            status = orderStatusMapper.toOrderStatus(orderDataEntity.status),
            createdAt = dateTimeFormatter.formatDataTime(orderDataEntity.createdAtMillis),
            orderDeliverInfo = orderDataEntity.recipient.toDeliverInfo(),
            orderItems = orderDataEntity.items.map {
                OrderItem(
                    id = it.id,
                    name = it.productName,
                    quantity = it.quantity,
                    price = OrderUsdPrice(it.priceUsdCents, priceFormatter)
                )
            }
        )
    }

    private fun RecipientDataEntity.toDeliverInfo(): String {
        return "$firstname $lastname $address"
    }
}