package com.mcmouse88.multimodulefeature.core.common

interface Logger {

    fun log(message: String)

    fun err(exception: Throwable, message: String? = null)
}