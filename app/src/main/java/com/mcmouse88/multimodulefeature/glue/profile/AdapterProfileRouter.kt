package com.mcmouse88.multimodulefeature.glue.profile

import com.mcmouse88.multimodulefeature.R
import com.mcmouse88.multimodulefeature.core.common.AppRestarter
import com.mcmouse88.multimodulefeature.navigation.GlobalNavComponentRouter
import com.mcmouse88.multimodulefeature.profile.presentation.ProfileRouter
import javax.inject.Inject

class AdapterProfileRouter @Inject constructor(
    private val appRestarter: AppRestarter,
    private val globalNavComponentRouter: GlobalNavComponentRouter
) : ProfileRouter {

    override fun launchEditUsername() {
        globalNavComponentRouter.launch(R.id.editUsernameFragment)
    }

    override fun goBack() {
        globalNavComponentRouter.pop()
    }

    override fun restartApp() {
        appRestarter.restartApp()
    }
}