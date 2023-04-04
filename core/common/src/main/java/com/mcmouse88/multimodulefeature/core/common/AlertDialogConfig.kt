package com.mcmouse88.multimodulefeature.core.common

data class AlertDialogConfig(
    val title: String,
    val message: String,
    val positiveButton: String,
    val negativeButton: String? = null
)