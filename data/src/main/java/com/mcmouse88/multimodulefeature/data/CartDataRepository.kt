package com.mcmouse88.multimodulefeature.data

import com.mcmouse88.multimodulefeature.core.common.Container
import com.mcmouse88.multimodulefeature.data.cart.entities.CartItemDataEntity
import kotlinx.coroutines.flow.Flow

interface CartDataRepository {

    fun getCart(): Flow<Container<List<CartItemDataEntity>>>

    suspend fun addToCart(productId: Long, quantity: Int)

    suspend fun getCartItemById(id: Long): CartItemDataEntity

    suspend fun deleteCartItem(ids: List<Long>)

    suspend fun deleteAll()

    suspend fun changeQuantity(cartId: Long, quantity: Int)

    fun reload()
}