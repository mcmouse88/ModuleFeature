package com.mcmouse88.multimodulefeature.data.accounts.di

import com.mcmouse88.multimodulefeature.data.AccountsDataRepository
import com.mcmouse88.multimodulefeature.data.accounts.RealAccountsDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
interface AccountRepositoriesModule {

    @[Binds Singleton]
    fun bindAccountsRepository(
        accountsDataRepository: RealAccountsDataRepository
    ): AccountsDataRepository
}