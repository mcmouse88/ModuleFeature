package com.mcmouse88.multimodulefeature.core.common.flow

interface LazyFlowSubjectFactory {
    fun <T> create(loader: ValueLoader<T>): LazyFlowSubject<T>
}