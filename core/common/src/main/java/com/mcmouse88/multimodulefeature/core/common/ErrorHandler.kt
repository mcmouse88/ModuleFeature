package com.mcmouse88.multimodulefeature.core.common

interface ErrorHandler {

    fun handleError(exception: Throwable)

    fun getUserMessage(exception: Throwable): String
}