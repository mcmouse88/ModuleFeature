package com.mcmouse88.multimodulefeature.data.orders.di

import com.mcmouse88.multimodulefeature.data.orders.sources.InMemoryOrdersDataSource
import com.mcmouse88.multimodulefeature.data.orders.sources.OrdersDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
interface OrdersSourcesModule {

    @[Binds Singleton]
    fun bindOrdersDataSource(
        ordersDataSource: InMemoryOrdersDataSource
    ): OrdersDataSource
}