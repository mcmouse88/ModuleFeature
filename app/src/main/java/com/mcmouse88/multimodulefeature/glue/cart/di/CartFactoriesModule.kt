package com.mcmouse88.multimodulefeature.glue.cart.di

import com.mcmouse88.multimodulefeature.cart.domain.factories.PriceFactory
import com.mcmouse88.multimodulefeature.glue.cart.factories.DefaultCartPriceFactories
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
interface CartFactoriesModule {

    @[Binds Singleton]
    fun bindPriceFactory(priceFactory: DefaultCartPriceFactories): PriceFactory
}