package com.mcmouse88.multimodulefeature.data.orders.sources

import com.github.javafaker.Faker
import com.mcmouse88.multimodulefeature.core.common.NotFoundException
import com.mcmouse88.multimodulefeature.data.orders.entities.*
import com.mcmouse88.multimodulefeature.data.products.sources.ProductsDataSource
import kotlinx.coroutines.delay
import java.util.*
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class InMemoryOrdersDataSource @Inject constructor(
    private val productsDataSource: ProductsDataSource
) : OrdersDataSource {

    private val faker = Faker.instance(Random(42))
    private val random = Random(42)

    private val orders = createOrders()

    override suspend fun getOrders(): List<OrderDataEntity> {
        return orders.sortedByDescending { it.createdAtMillis }
    }

    override suspend fun setStatus(uuid: String, newStatus: OrderStatusDataValue) {
        val index = orders.indexOfFirst { it.uuid == uuid }
        if (index == -1) throw NotFoundException()
        delay(1.seconds)
        val updatedOrder = orders[index].copy(
            status = newStatus
        )
        orders[index] = updatedOrder
    }

    override suspend fun createOrder(createOrderDataEntity: CreateOrderDataEntity) {
        delay(1.seconds)
        val newOrder = OrderDataEntity(
            uuid = UUID.randomUUID().toString().substring(0, 18),
            recipient = createOrderDataEntity.recipientDataEntity,
            status = OrderStatusDataValue.CREATED,
            createdAtMillis = System.currentTimeMillis(),
            items = createOrderDataEntity.items.map {
                OrderItemDataEntity(
                    id = UUID.randomUUID().toString(),
                    productName = productsDataSource.getProductById(it.productId).name,
                    quantity = it.quantity,
                    priceUsdCents = it.priceUsdCents
                )
            }
        )
        orders.add(newOrder)
    }

    private fun createOrders(): MutableList<OrderDataEntity> {
        return MutableList(7) {
            OrderDataEntity(
                uuid = UUID.randomUUID().toString().substring(0, 18),
                recipient = RecipientDataEntity(
                    firstname = faker.name().firstName(),
                    lastname = faker.name().lastName(),
                    address = faker.address().fullAddress()
                ),
                status = OrderStatusDataValue.values()[it % OrderStatusDataValue.values().size],
                createdAtMillis = System.currentTimeMillis() - 1000 * 3600 * 6 * (random.nextInt(10) + 10),
                items = List(random.nextInt(5) + 1) {
                    OrderItemDataEntity(
                        id = UUID.randomUUID().toString(),
                        productName = faker.food().dish(),
                        quantity = random.nextInt(6) + 1,
                        priceUsdCents = 1000 + random.nextInt(20000)
                    )
                }
            )
        }
    }
}