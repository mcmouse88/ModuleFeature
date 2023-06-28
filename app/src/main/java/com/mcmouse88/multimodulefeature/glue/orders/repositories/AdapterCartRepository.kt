package com.mcmouse88.multimodulefeature.glue.orders.repositories

import com.mcmouse88.multimodulefeature.core.common.unwrapFirst
import com.mcmouse88.multimodulefeature.data.CartDataRepository
import com.mcmouse88.multimodulefeature.data.ProductsDataRepository
import com.mcmouse88.multimodulefeature.formatters.PriceFormatter
import com.mcmouse88.multimodulefeature.glue.orders.entities.OrderUsdPrice
import com.mcmouse88.multimodulefeature.orders.domain.entities.Cart
import com.mcmouse88.multimodulefeature.orders.domain.entities.CartItem
import com.mcmouse88.multimodulefeature.orders.domain.factories.PriceFactory
import com.mcmouse88.multimodulefeature.orders.domain.repositories.CartRepository
import javax.inject.Inject

class AdapterCartRepository @Inject constructor(
    private val cartDataRepository: CartDataRepository,
    private val productsDataRepository: ProductsDataRepository,
    private val priceFormatter: PriceFormatter,
    private val priceFactory: PriceFactory
) : CartRepository {

    override suspend fun getCart(): Cart {
        val cartItems = cartDataRepository.getCart().unwrapFirst()
        return Cart(
            cartItems = cartItems.map {
                val product = productsDataRepository.getProductById(it.productId)
                val priceWithDiscount = productsDataRepository.getDiscountPriceUsdCentsForEntity(product)
                    ?: product.priceUsdCents
                CartItem(
                    name = product.name,
                    productId = it.productId,
                    price = OrderUsdPrice(priceWithDiscount, priceFormatter),
                    quantity = it.quantity
                )
            },
            priceFactory = priceFactory
        )
    }

    override suspend fun cleanUpCart() {
        cartDataRepository.deleteAll()
    }
}