package com.mcmouse88.multimodulefeature.glue.catalog.di

import com.mcmouse88.multimodulefeature.catalog.presentation.CatalogFilterRouter
import com.mcmouse88.multimodulefeature.catalog.presentation.CatalogRouter
import com.mcmouse88.multimodulefeature.glue.catalog.AdapterCatalogFilterRouter
import com.mcmouse88.multimodulefeature.glue.catalog.AdapterCatalogRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@[Module InstallIn(ActivityRetainedComponent::class)]
interface RouterModule {

    @Binds
    fun bindCatalogRouter(
        catalogRouter: AdapterCatalogRouter
    ): CatalogRouter

    @Binds
    fun bindCatalogFilterRouter(
        router: AdapterCatalogFilterRouter
    ): CatalogFilterRouter
}