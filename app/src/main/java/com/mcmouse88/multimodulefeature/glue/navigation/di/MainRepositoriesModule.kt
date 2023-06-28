package com.mcmouse88.multimodulefeature.glue.navigation.di

import com.mcmouse88.multimodulefeature.glue.navigation.repositories.AdapterGetCartItemCountRepository
import com.mcmouse88.multimodulefeature.glue.navigation.repositories.AdapterGetCurrentUsernameRepository
import com.mcmouse88.multimodulefeature.navigation.domain.repositories.GetCartItemsCountsRepository
import com.mcmouse88.multimodulefeature.navigation.domain.repositories.GetCurrentUsernameRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@[Module InstallIn(SingletonComponent::class)]
interface MainRepositoriesModule {

    @Binds
    fun bindGetCurrentUsernameRepository(
        getCurrentUsernameRepository: AdapterGetCurrentUsernameRepository
    ): GetCurrentUsernameRepository

    @Binds
    fun bindGetCartItemCountRepository(
        getCartItemCountRepository: AdapterGetCartItemCountRepository
    ): GetCartItemsCountsRepository
}