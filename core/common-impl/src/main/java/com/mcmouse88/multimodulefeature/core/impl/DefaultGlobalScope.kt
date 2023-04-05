package com.mcmouse88.multimodulefeature.core.impl

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

fun createDefaultGlobalScope(): CoroutineScope {
    val errorHandler = CoroutineExceptionHandler { _, exception ->
        Log.e("DefaultGlobalScope", "Error!!", exception)
    }
    return CoroutineScope(SupervisorJob() + Dispatchers.Main + errorHandler)
}
