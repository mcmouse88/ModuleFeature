package com.mcmouse88.multimodulefeature.sign_in.presentation

interface SignInRouter {
    fun launchSignUp(email: String = "")
    fun launchMain()
}