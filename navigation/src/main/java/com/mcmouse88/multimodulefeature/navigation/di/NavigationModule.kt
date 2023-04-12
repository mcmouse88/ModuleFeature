package com.mcmouse88.multimodulefeature.navigation.di

import com.mcmouse88.multimodulefeature.core.common.AppRestarter
import com.mcmouse88.multimodulefeature.core.impl.ActivityRequired
import com.mcmouse88.multimodulefeature.navigation.GlobalNavComponentRouter
import com.mcmouse88.multimodulefeature.navigation.MainAppRestarter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@[Module InstallIn(SingletonComponent::class)]
class NavigationModule {

    @Provides
    fun provideAppRestarted(
        appRestarted: MainAppRestarter
    ): AppRestarter {
        return appRestarted
    }

    @[Provides IntoSet]
    fun provideRouterAsActivityRequired(
        router: GlobalNavComponentRouter
    ): ActivityRequired {
        return router
    }
}