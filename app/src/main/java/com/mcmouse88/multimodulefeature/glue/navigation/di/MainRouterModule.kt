package com.mcmouse88.multimodulefeature.glue.navigation.di

import com.mcmouse88.multimodulefeature.glue.navigation.DefaultMainRouter
import com.mcmouse88.multimodulefeature.navigation.presentation.MainRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@[Module InstallIn(ActivityRetainedComponent::class)]
interface MainRouterModule {

    @Binds
    fun bindMainRouter(router: DefaultMainRouter): MainRouter
}