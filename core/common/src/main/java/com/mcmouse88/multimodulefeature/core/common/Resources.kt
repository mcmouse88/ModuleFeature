package com.mcmouse88.multimodulefeature.core.common

interface Resources {

    fun getString(id: Int): String

    fun getString(id: Int, vararg placeholder: Any): String
}