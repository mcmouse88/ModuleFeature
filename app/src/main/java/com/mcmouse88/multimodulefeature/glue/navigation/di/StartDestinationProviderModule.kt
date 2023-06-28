package com.mcmouse88.multimodulefeature.glue.navigation.di

import com.mcmouse88.multimodulefeature.glue.navigation.DefaultDestinationsProvider
import com.mcmouse88.multimodulefeature.navigation.DestinationsProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@[Module InstallIn(SingletonComponent::class)]
interface StartDestinationProviderModule {

    @Binds
    fun bindStartDestinationProvider(
        startDestinationProvider: DefaultDestinationsProvider
    ): DestinationsProvider
}