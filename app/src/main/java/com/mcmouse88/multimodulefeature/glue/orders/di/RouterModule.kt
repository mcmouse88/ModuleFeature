package com.mcmouse88.multimodulefeature.glue.orders.di

import com.mcmouse88.multimodulefeature.glue.orders.AdapterOrderRouter
import com.mcmouse88.multimodulefeature.orders.presentation.OrdersRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@[Module InstallIn(ActivityRetainedComponent::class)]
interface RouterModule {

    @Binds
    fun bindRouter(ordersRouter: AdapterOrderRouter): OrdersRouter
}