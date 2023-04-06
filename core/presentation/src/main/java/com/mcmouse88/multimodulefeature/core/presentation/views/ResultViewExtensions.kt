package com.mcmouse88.multimodulefeature.core.presentation.views

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.mcmouse88.multimodulefeature.core.common.Container
import com.mcmouse88.multimodulefeature.core.presentation.live.LiveValue
import com.mcmouse88.multimodulefeature.core.presentation.observeStateOn
import kotlinx.coroutines.flow.Flow

fun <T> ResultView.observe(
    owner: LifecycleOwner,
    liveData: LiveData<Container<T>>,
    onSuccess: (T) -> Unit
) {
    liveData.observe(owner) {
        container = it
        if (it is Container.Success) {
            onSuccess(it.value)
        }
    }
}

fun <T> ResultView.observe(
    owner: LifecycleOwner,
    liveValue: LiveValue<Container<T>>,
    onSuccess: (T) -> Unit
) {
    liveValue.observe(owner) {
        container = it
        if (it is Container.Success) {
            onSuccess(it.value)
        }
    }
}

fun <T> ResultView.observe(
    owner: LifecycleOwner,
    flow: Flow<Container<T>>,
    onSuccess: (T) -> Unit
) {
    flow.observeStateOn(owner) {
        container = it
        if (it is Container.Success) {
            onSuccess(it.value)
        }
    }
}