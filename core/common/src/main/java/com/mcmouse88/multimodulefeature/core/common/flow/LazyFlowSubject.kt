package com.mcmouse88.multimodulefeature.core.common.flow

import com.mcmouse88.multimodulefeature.core.common.Container
import kotlinx.coroutines.flow.Flow

typealias ValueLoader<T> = suspend () -> T

interface LazyFlowSubject<T> {

    fun listen(): Flow<Container<T>>

    suspend fun newLoad(silently: Boolean = false, valueLoader: ValueLoader<T>? = null): T

    fun newAsyncLoad(silently: Boolean = false, valueLoader: ValueLoader<T>? = null)

    fun updateWith(container: Container<T>)

    fun updateWith(updater: (Container<T>) -> Container<T>)
}

