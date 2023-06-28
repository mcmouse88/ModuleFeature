package com.mcmouse88.multimodulefeature.glue.cart

import com.mcmouse88.multimodulefeature.R
import com.mcmouse88.multimodulefeature.cart.presentation.CartRouter
import com.mcmouse88.multimodulefeature.cart.presentation.quantity.EditQuantityDialogFragment
import com.mcmouse88.multimodulefeature.cart.presentation.quantity.EditQuantityResult
import com.mcmouse88.multimodulefeature.catalog.presentation.details.ProductDetailFragment
import com.mcmouse88.multimodulefeature.core.common.ScreenCommunication
import com.mcmouse88.multimodulefeature.core.common.listen
import com.mcmouse88.multimodulefeature.navigation.GlobalNavComponentRouter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AdapterCartRouter @Inject constructor(
    private val globalNavComponentRouter: GlobalNavComponentRouter,
    private val screenCommunication: ScreenCommunication
) : CartRouter {

    override fun launchProductDetails(productId: Long) {
        globalNavComponentRouter.launch(
            R.id.productDetailsFragment,
            ProductDetailFragment.Screen(productId)
        )
    }

    override fun goBack() {
        globalNavComponentRouter.pop()
    }

    override fun launchEditQuantity(cartItemId: Long, initialQty: Int) {
        globalNavComponentRouter.launch(
            R.id.editQuantityDialogFragment,
            EditQuantityDialogFragment.Screen(cartItemId, initialQty)
        )
    }

    override fun launchCreateOrder() {
        globalNavComponentRouter.launch(R.id.createOrderFragment)
    }

    override fun receiveQuantity(): Flow<EditQuantityResult> {
        return screenCommunication.listen(EditQuantityResult::class.java)
    }

    override fun sendQuantity(editQuantityResult: EditQuantityResult) {
        screenCommunication.publishResult(editQuantityResult)
    }

    override fun registerBackHandler(scope: CoroutineScope, handler: () -> Boolean) {
        globalNavComponentRouter.requireBackHandler(scope, handler)
    }
}