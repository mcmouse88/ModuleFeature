package com.mcmouse88.multimodulefeature.glue.cart.repositories

import com.mcmouse88.multimodulefeature.cart.domain.entities.CartItem
import com.mcmouse88.multimodulefeature.cart.domain.repositories.CartRepository
import com.mcmouse88.multimodulefeature.core.common.Container
import com.mcmouse88.multimodulefeature.data.CartDataRepository
import com.mcmouse88.multimodulefeature.glue.cart.mappers.CartItemMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AdapterCartRepository @Inject constructor(
    private val cartDataRepository: CartDataRepository,
    private val cartItemMapper: CartItemMapper
) : CartRepository {

    override suspend fun changeQuantity(cartItemId: Long, newQuantity: Int) {
        cartDataRepository.changeQuantity(cartItemId, newQuantity)
    }

    override suspend fun deleteCartItems(cartItemIds: List<Long>) {
        cartDataRepository.deleteCartItem(cartItemIds)
    }

    override suspend fun getCartItemById(id: Long): CartItem {
        return cartItemMapper.toCartItem(cartDataRepository.getCartItemById(id))
    }

    override fun getCartItems(): Flow<Container<List<CartItem>>> {
        return cartDataRepository.getCart().map { container ->
            container.suspendMap { list ->
                list.map { cartItemMapper.toCartItem(it) }
            }
        }
    }

    override fun reload() {
        cartDataRepository.reload()
    }
}