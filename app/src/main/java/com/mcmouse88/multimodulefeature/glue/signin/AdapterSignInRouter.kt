package com.mcmouse88.multimodulefeature.glue.signin

import com.mcmouse88.multimodulefeature.R
import com.mcmouse88.multimodulefeature.navigation.GlobalNavComponentRouter
import com.mcmouse88.multimodulefeature.sign_in.presentation.SignInRouter
import com.mcmouse88.multimodulefeature.sign_up.presentation.signup.SignUpFragment
import javax.inject.Inject

class AdapterSignInRouter @Inject constructor(
    private val globalNavComponentRouter: GlobalNavComponentRouter
) : SignInRouter {

    override fun launchSignUp(email: String) {
        val screen = SignUpFragment.Screen(email)
        globalNavComponentRouter.launch(R.id.signUpFragment, screen)
    }

    override fun launchMain() {
        globalNavComponentRouter.startTabs()
    }
}