package com.mcmouse88.multimodulefeature.navigation

import com.mcmouse88.multimodulefeature.core.common.AppRestarter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainAppRestarter @Inject constructor(
    private val globalNavComponentRouter: GlobalNavComponentRouter
) : AppRestarter {

    override fun restartApp() {
        globalNavComponentRouter.restart()
    }
}