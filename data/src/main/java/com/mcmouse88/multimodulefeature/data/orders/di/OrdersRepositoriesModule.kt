package com.mcmouse88.multimodulefeature.data.orders.di

import com.mcmouse88.multimodulefeature.data.OrdersDataRepository
import com.mcmouse88.multimodulefeature.data.orders.RealOrdersDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
interface OrdersRepositoriesModule {

    @[Binds Singleton]
    fun bindOrdersRepository(
        ordersDataRepository: RealOrdersDataRepository
    ): OrdersDataRepository
}