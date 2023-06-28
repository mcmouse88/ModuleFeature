package com.mcmouse88.multimodulefeature.glue.orders.di

import com.mcmouse88.multimodulefeature.glue.orders.repositories.AdapterCartRepository
import com.mcmouse88.multimodulefeature.glue.orders.repositories.AdapterOrderRepository
import com.mcmouse88.multimodulefeature.glue.orders.repositories.AdapterProductRepository
import com.mcmouse88.multimodulefeature.orders.domain.repositories.CartRepository
import com.mcmouse88.multimodulefeature.orders.domain.repositories.OrdersRepository
import com.mcmouse88.multimodulefeature.orders.domain.repositories.ProductsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@[Module InstallIn(SingletonComponent::class)]
interface OrderRepositoriesModule {

    @Binds
    fun bindOrdersRepository(
        ordersRepository: AdapterOrderRepository
    ): OrdersRepository

    @Binds
    fun bindCartRepository(
        cartRepository: AdapterCartRepository
    ): CartRepository

    @Binds
    fun bindProductsRepository(
        productsRepository: AdapterProductRepository
    ): ProductsRepository
}