package com.mcmouse88.multimodulefeature.glue.orders.di

import com.mcmouse88.multimodulefeature.glue.orders.factories.DefaultOrderPriceFactory
import com.mcmouse88.multimodulefeature.orders.domain.factories.PriceFactory
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@[Module InstallIn(SingletonComponent::class)]
interface OrderFactoriesModule {

    @Binds
    fun bindOrderPriceFactory(
        priceFactory: DefaultOrderPriceFactory
    ): PriceFactory
}