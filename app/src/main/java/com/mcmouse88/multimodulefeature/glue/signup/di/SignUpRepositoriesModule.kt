package com.mcmouse88.multimodulefeature.glue.signup.di

import com.mcmouse88.multimodulefeature.glue.signup.repositories.AdapterSignUpRepository
import com.mcmouse88.multimodulefeature.sign_up.domain.repositories.SignUpRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@[Module InstallIn(SingletonComponent::class)]
interface SignUpRepositoriesModule {

    @Binds
    fun bindSignUpDataSource(signUpDataSource: AdapterSignUpRepository): SignUpRepository
}