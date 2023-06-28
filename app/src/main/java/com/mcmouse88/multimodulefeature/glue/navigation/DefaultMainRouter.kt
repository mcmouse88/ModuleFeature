package com.mcmouse88.multimodulefeature.glue.navigation

import com.mcmouse88.multimodulefeature.R
import com.mcmouse88.multimodulefeature.navigation.GlobalNavComponentRouter
import com.mcmouse88.multimodulefeature.navigation.presentation.MainRouter
import javax.inject.Inject

class DefaultMainRouter @Inject constructor(
    private val navComponentRouter: GlobalNavComponentRouter
) : MainRouter {

    override fun launchCart() {
        navComponentRouter.launch(R.id.cartListFragment)
    }
}