package com.mcmouse88.multimodulefeature.glue.signup

import com.mcmouse88.multimodulefeature.navigation.GlobalNavComponentRouter
import com.mcmouse88.multimodulefeature.sign_up.presentation.SignUpRouter
import javax.inject.Inject

class AdapterSignUpRouter @Inject constructor(
    private val globalNavComponentRouter: GlobalNavComponentRouter
) : SignUpRouter {

    override fun goBack() {
        globalNavComponentRouter.pop()
    }
}