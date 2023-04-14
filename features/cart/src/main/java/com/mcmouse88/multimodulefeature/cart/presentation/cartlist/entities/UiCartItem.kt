package com.mcmouse88.multimodulefeature.cart.presentation.cartlist.entities

import com.mcmouse88.multimodulefeature.cart.domain.entities.CartItem
import com.mcmouse88.multimodulefeature.cart.domain.entities.Price
import com.mcmouse88.multimodulefeature.cart.domain.entities.Product

data class UiCartItem(
    val origin: CartItem,
    val isChecked: Boolean,
    val showCheckbox: Boolean,
    val minQuantity: Int,
    val maxQuantity: Int
) {

    val id: Long get() = origin.id
    val product: Product get() = origin.product
    val imageUrl: String get() = product.photo
    val quantity: Int get() = origin.quantity
    val name: String get() = product.name
    val totalOriginPrice: Price get() = origin.totalPrice
    val totalDiscountPrice: Price get() = origin.totalPriceWithDiscount
    val canIncrement: Boolean get() = quantity < maxQuantity
    val canDecrement: Boolean get() = quantity > minQuantity
}