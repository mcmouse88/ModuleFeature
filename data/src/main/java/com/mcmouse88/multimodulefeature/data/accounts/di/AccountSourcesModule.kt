package com.mcmouse88.multimodulefeature.data.accounts.di

import com.mcmouse88.multimodulefeature.data.accounts.sources.AccountsDataSource
import com.mcmouse88.multimodulefeature.data.accounts.sources.InMemoryAccountsDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
interface AccountSourcesModule {

    @[Binds Singleton]
    fun bindAccountSource(
        accountsDataSource: InMemoryAccountsDataSource
    ): AccountsDataSource
}