package com.mcmouse88.multimodulefeature.glue.signin.di

import com.mcmouse88.multimodulefeature.glue.signin.AdapterSignInRouter
import com.mcmouse88.multimodulefeature.sign_in.presentation.SignInRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@[Module InstallIn(ActivityRetainedComponent::class)]
interface SignInRouterModule {

    @Binds
    fun bindSignInRouter(router: AdapterSignInRouter): SignInRouter
}