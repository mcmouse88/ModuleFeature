package com.mcmouse88.multimodulefeature.data.cart.sources

import com.mcmouse88.multimodulefeature.data.cart.entities.CartItemDataEntity

interface CartDataSource {

    suspend fun clearCart()

    suspend fun getCart(): List<CartItemDataEntity>

    suspend fun saveToCart(productId: Long, quantity: Int)

    suspend fun delete(cartItemId: Long)

    suspend fun deleteAll()
}