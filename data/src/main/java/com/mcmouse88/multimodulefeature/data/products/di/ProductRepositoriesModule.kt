package com.mcmouse88.multimodulefeature.data.products.di

import com.mcmouse88.multimodulefeature.data.ProductsDataRepository
import com.mcmouse88.multimodulefeature.data.products.RealProductsDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
interface ProductRepositoriesModule {

    @[Binds Singleton]
    fun bindProductRepository(
        productsDataRepository: RealProductsDataRepository
    ): ProductsDataRepository
}