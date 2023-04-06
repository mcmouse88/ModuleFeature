package com.mcmouse88.multimodulefeature.core.presentation

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun <T> Flow<T>.observeStateOn(owner: LifecycleOwner, block: (T) -> Unit) {
    owner.lifecycleScope.launch {
        owner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            collectLatest { block(it) }
        }
    }
}
