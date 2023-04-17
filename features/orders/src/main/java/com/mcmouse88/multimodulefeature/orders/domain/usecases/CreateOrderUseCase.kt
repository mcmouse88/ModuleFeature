package com.mcmouse88.multimodulefeature.orders.domain.usecases

import com.mcmouse88.multimodulefeature.orders.domain.entities.Cart
import com.mcmouse88.multimodulefeature.orders.domain.entities.Field
import com.mcmouse88.multimodulefeature.orders.domain.entities.Recipient
import com.mcmouse88.multimodulefeature.orders.domain.exceptions.EmptyFieldException
import com.mcmouse88.multimodulefeature.orders.domain.repositories.CartRepository
import com.mcmouse88.multimodulefeature.orders.domain.repositories.OrdersRepository
import com.mcmouse88.multimodulefeature.orders.domain.repositories.ProductsRepository
import javax.inject.Inject

class CreateOrderUseCase @Inject constructor(
    private val ordersRepository: OrdersRepository,
    private val cartRepository: CartRepository,
    private val productsRepository: ProductsRepository
) {

    suspend fun createOrder(cart: Cart, recipient: Recipient) {
        if (recipient.firstName.isBlank()) throw EmptyFieldException(Field.FIRST_NAME)
        if (recipient.lastName.isBlank()) throw EmptyFieldException(Field.LAST_NAME)
        if (recipient.address.isBlank()) throw EmptyFieldException(Field.ADDRESS)

        ordersRepository.makeOrder(cart, recipient)
        cartRepository.cleanUpCart()
        cart.cartItems.forEach { cartItem ->
            productsRepository.changeQuantityBy(cartItem.productId, -cartItem.quantity)
        }
    }
}