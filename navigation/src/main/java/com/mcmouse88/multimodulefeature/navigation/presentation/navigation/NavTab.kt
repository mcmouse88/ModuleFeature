package com.mcmouse88.multimodulefeature.navigation.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import java.io.Serializable

class NavTab(
    @IdRes val destinationId: Int,
    val title: String,
    @DrawableRes val iconRes: Int
) : Serializable
