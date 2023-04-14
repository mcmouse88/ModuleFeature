package com.mcmouse88.multimodulefeature.cart.domain.repositories

import com.mcmouse88.multimodulefeature.cart.domain.entities.CartItem
import com.mcmouse88.multimodulefeature.core.common.Container
import kotlinx.coroutines.flow.Flow

interface CartRepository {

    suspend fun changeQuantity(cartItemId: Long, newQuantity: Int)

    suspend fun deleteCartItems(cartItemIds: List<Long>)

    suspend fun getCartItemById(id: Long): CartItem

    fun getCartItems(): Flow<Container<List<CartItem>>>

    fun reload()
}