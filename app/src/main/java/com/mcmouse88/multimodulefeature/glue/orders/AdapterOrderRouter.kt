package com.mcmouse88.multimodulefeature.glue.orders

import com.mcmouse88.multimodulefeature.R
import com.mcmouse88.multimodulefeature.navigation.GlobalNavComponentRouter
import com.mcmouse88.multimodulefeature.orders.presentation.OrdersRouter
import javax.inject.Inject

class AdapterOrderRouter @Inject constructor(
    private val globalNavComponentRouter: GlobalNavComponentRouter
) : OrdersRouter {

    override fun launchOrdersTab() {
        globalNavComponentRouter.startTabs(startTabDestinationId = R.id.ordersListFragment)
    }
}