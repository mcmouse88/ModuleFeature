package com.mcmouse88.multimodulefeature.glue.cart.di

import com.mcmouse88.multimodulefeature.cart.presentation.CartRouter
import com.mcmouse88.multimodulefeature.glue.cart.AdapterCartRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@[Module InstallIn(ActivityRetainedComponent::class)]
interface CartRouterModule {
    @Binds
    fun bindCartRouter(cartRouter: AdapterCartRouter): CartRouter
}