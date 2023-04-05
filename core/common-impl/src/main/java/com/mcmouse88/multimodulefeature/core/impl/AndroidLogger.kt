package com.mcmouse88.multimodulefeature.core.impl

import android.util.Log
import com.mcmouse88.multimodulefeature.core.common.Logger

class AndroidLogger : Logger {

    override fun log(message: String) {
        Log.d(javaClass.simpleName, message)
    }

    override fun err(exception: Throwable, message: String?) {
        Log.e(javaClass.simpleName, message, exception)
    }
}