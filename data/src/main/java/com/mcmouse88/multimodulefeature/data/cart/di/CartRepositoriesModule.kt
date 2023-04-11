package com.mcmouse88.multimodulefeature.data.cart.di

import com.mcmouse88.multimodulefeature.data.CartDataRepository
import com.mcmouse88.multimodulefeature.data.cart.RealCartDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
interface CartRepositoriesModule {

    @[Binds Singleton]
    fun bindCartRepository(
        cartDataRepository: RealCartDataRepository
    ): CartDataRepository
}