package com.mcmouse88.multimodulefeature.cart.presentation

import com.mcmouse88.multimodulefeature.cart.presentation.quantity.EditQuantityResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface CartRouter {

    fun launchProductDetails(productId: Long)

    fun goBack()

    fun launchEditQuantity(cartItemId: Long, initialQty: Int)

    fun launchCreateOrder()

    fun receiveQuantity(): Flow<EditQuantityResult>

    fun sendQuantity(editQuantityResult: EditQuantityResult)

    fun registerBackHandler(scope: CoroutineScope, handler: () -> Boolean)
}