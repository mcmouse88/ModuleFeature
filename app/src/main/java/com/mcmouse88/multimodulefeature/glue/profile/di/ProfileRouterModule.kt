package com.mcmouse88.multimodulefeature.glue.profile.di

import com.mcmouse88.multimodulefeature.glue.profile.AdapterProfileRouter
import com.mcmouse88.multimodulefeature.profile.presentation.ProfileRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@[Module InstallIn(ActivityRetainedComponent::class)]
interface ProfileRouterModule {

    @Binds
    fun bindProfileRouter(
        profileRouter: AdapterProfileRouter
    ): ProfileRouter
}