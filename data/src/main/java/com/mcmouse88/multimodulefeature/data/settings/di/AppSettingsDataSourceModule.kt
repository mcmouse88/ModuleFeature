package com.mcmouse88.multimodulefeature.data.settings.di

import com.mcmouse88.multimodulefeature.data.settings.SettingsDataSource
import com.mcmouse88.multimodulefeature.data.settings.SharedPreferencesSettingsDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
interface AppSettingsDataSourceModule {

    @[Binds Singleton]
    fun bindAppSettingsDataSource(
        settingsDataSource: SharedPreferencesSettingsDataSource
    ): SettingsDataSource
}