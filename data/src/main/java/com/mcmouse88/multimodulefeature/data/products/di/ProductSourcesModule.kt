package com.mcmouse88.multimodulefeature.data.products.di

import com.mcmouse88.multimodulefeature.data.products.sources.DiscountsDataSource
import com.mcmouse88.multimodulefeature.data.products.sources.InMemoryDiscountsDataSource
import com.mcmouse88.multimodulefeature.data.products.sources.InMemoryProductsDataSource
import com.mcmouse88.multimodulefeature.data.products.sources.ProductsDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
interface ProductSourcesModule {

    @[Binds Singleton]
    fun bindProductsSource(
        productsDataSource: InMemoryProductsDataSource
    ): ProductsDataSource

    @[Binds Singleton]
    fun bindDiscountSource(
        discountsDataSource: InMemoryDiscountsDataSource
    ): DiscountsDataSource
}