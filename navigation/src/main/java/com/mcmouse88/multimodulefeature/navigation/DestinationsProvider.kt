package com.mcmouse88.multimodulefeature.navigation

import androidx.annotation.IdRes
import androidx.annotation.NavigationRes
import com.mcmouse88.multimodulefeature.navigation.presentation.navigation.NavTab

interface DestinationsProvider {

    @IdRes
    fun provideStartDestinationId(): Int

    @NavigationRes
    fun provideNavigationGraphId(): Int

    fun provideMainTabs(): List<NavTab>

    @IdRes
    fun provideTabsDestinationId(): Int

    @IdRes
    fun provideCartDestinationId(): Int
}