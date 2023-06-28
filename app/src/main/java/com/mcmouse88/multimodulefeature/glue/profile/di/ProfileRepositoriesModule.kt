package com.mcmouse88.multimodulefeature.glue.profile.di

import com.mcmouse88.multimodulefeature.glue.profile.repositories.AdapterAuthTokenRepository
import com.mcmouse88.multimodulefeature.glue.profile.repositories.AdapterProfileRepository
import com.mcmouse88.multimodulefeature.profile.domain.repositories.AuthTokenRepository
import com.mcmouse88.multimodulefeature.profile.domain.repositories.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@[Module InstallIn(SingletonComponent::class)]
interface ProfileRepositoriesModule {

    @Binds
    fun bindAuthTokenRepository(
        authTokenRepository: AdapterAuthTokenRepository
    ): AuthTokenRepository

    @Binds
    fun bindProfileRepository(
        profileRepository: AdapterProfileRepository
    ): ProfileRepository
}