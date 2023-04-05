package com.mcmouse88.multimodulefeature.core.impl

import android.content.Context
import com.mcmouse88.multimodulefeature.core.common.Resources

class AndroidResources(
    private val applicationContext: Context
) : Resources {

    override fun getString(id: Int): String {
        return applicationContext.getString(id)
    }

    override fun getString(id: Int, vararg placeholder: Any): String {
        return applicationContext.getString(id, placeholder)
    }
}