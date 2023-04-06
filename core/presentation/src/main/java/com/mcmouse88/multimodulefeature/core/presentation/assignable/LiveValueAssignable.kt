package com.mcmouse88.multimodulefeature.core.presentation.assignable

import com.mcmouse88.multimodulefeature.core.presentation.live.LiveValue
import com.mcmouse88.multimodulefeature.core.presentation.live.MutableLiveValue

internal class LiveValueAssignable<T>(
    private val liveValue: LiveValue<T>
) : Assignable<T> {

    override fun setValue(value: T) {
        (liveValue as? MutableLiveValue<T>)?.setValue(value)
    }
}