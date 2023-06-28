package com.mcmouse88.multimodulefeature.glue.catalog.di

import com.mcmouse88.multimodulefeature.catalog.domain.repositories.CartRepository
import com.mcmouse88.multimodulefeature.catalog.domain.repositories.ProductRepository
import com.mcmouse88.multimodulefeature.glue.catalog.repositories.AdapterCartRepository
import com.mcmouse88.multimodulefeature.glue.catalog.repositories.AdapterProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@[Module InstallIn(SingletonComponent::class)]
interface RepositoriesModule {

    @Binds
    fun bindProductsRepository(
        repository: AdapterProductRepository
    ): ProductRepository

    @Binds
    fun provideCartRepository(
        repository: AdapterCartRepository
    ): CartRepository
}