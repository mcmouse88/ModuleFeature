package com.mcmouse88.multimodulefeature.glue.signup.di

import com.mcmouse88.multimodulefeature.glue.signup.AdapterSignUpRouter
import com.mcmouse88.multimodulefeature.sign_up.presentation.SignUpRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@[Module InstallIn(ActivityRetainedComponent::class)]
interface SignUpRouterModule {

    @Binds
    fun bindSignUpRouter(router: AdapterSignUpRouter): SignUpRouter
}