package com.mcmouse88.multimodulefeature.core.presentation.assignable

import kotlinx.coroutines.flow.MutableStateFlow

class StateFlowAssignable<T>(
    private val stateFlow: MutableStateFlow<T>
) : Assignable<T> {

    override fun setValue(value: T) {
        stateFlow.value = value
    }
}