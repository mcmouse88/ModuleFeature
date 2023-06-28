package com.mcmouse88.multimodulefeature.glue.cart.di

import com.mcmouse88.multimodulefeature.cart.domain.repositories.CartRepository
import com.mcmouse88.multimodulefeature.cart.domain.repositories.ProductRepository
import com.mcmouse88.multimodulefeature.glue.cart.repositories.AdapterCartRepository
import com.mcmouse88.multimodulefeature.glue.cart.repositories.AdapterProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@[Module InstallIn(SingletonComponent::class)]
interface CartRepositoriesModule {

    @Binds
    fun bindCartRepository(cartRepository: AdapterCartRepository): CartRepository

    @Binds
    fun bindProductRepository(productsRepository: AdapterProductRepository): ProductRepository
}