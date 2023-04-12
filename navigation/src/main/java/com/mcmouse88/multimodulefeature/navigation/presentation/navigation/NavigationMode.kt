package com.mcmouse88.multimodulefeature.navigation.presentation.navigation

import java.io.Serializable

sealed class NavigationMode : Serializable {

    object Stack : NavigationMode()

    class Tabs(
        val tabs: ArrayList<NavTab>,
        val startTabDestinationId: Int?
    ) : NavigationMode()
}