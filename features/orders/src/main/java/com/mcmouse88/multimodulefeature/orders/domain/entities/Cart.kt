package com.mcmouse88.multimodulefeature.orders.domain.entities

import com.mcmouse88.multimodulefeature.orders.domain.factories.PriceFactory

data class Cart(
    val cartItems: List<CartItem>,
    private val priceFactory: PriceFactory
) {

    val totalPrice: Price get() {
        return if (isEmpty) {
            priceFactory.zero
        } else {
            cartItems
                .map { it.price }
                .reduce { acc, price -> acc + price }
        }
    }

    private val isEmpty: Boolean get() = cartItems.isEmpty()
}