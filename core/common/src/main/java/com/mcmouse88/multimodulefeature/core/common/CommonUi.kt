package com.mcmouse88.multimodulefeature.core.common

interface CommonUi {
    fun toast(message: String)

    suspend fun alertDialog(config: AlertDialogConfig): Boolean
}