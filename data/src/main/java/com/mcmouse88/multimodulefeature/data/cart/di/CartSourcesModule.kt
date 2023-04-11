package com.mcmouse88.multimodulefeature.data.cart.di

import com.mcmouse88.multimodulefeature.data.cart.sources.CartDataSource
import com.mcmouse88.multimodulefeature.data.cart.sources.InMemoryCartDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
interface CartSourcesModule {

    @[Binds Singleton]
    fun bindCartSource(cartDataSource: InMemoryCartDataSource): CartDataSource
}