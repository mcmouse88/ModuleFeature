package com.mcmouse88.multimodulefeature.core.common.flow

import com.mcmouse88.multimodulefeature.core.common.Core
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

class DefaultLazyFlowSubjectFactory(
    private val dispatcher: CoroutineDispatcher,
    private val globalScope: CoroutineScope = Core.globalScope,
    private val cacheTimeoutMillis: Long = 1000
) : LazyFlowSubjectFactory {

    override fun <T> create(loader: ValueLoader<T>): LazyFlowSubject<T> {
        return DefaultLazyFlowSubject(loader, dispatcher, globalScope, cacheTimeoutMillis)
    }
}