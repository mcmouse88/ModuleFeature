@file:Suppress("UNCHECKED_CAST", "DEPRECATION")

package com.mcmouse88.multimodulefeature.core.presentation

import android.os.Build
import androidx.fragment.app.Fragment

const val ARG_SCREEN = "screen"

inline fun <reified T : BaseScreen> Fragment.args(): T {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        requireArguments().getSerializable(ARG_SCREEN, T::class.java)
            ?: throw IllegalStateException("Screen args don't exist")
    } else {
        requireArguments().getSerializable(ARG_SCREEN) as? T
            ?: throw IllegalStateException("Screen args don't exist")
    }
}