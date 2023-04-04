package com.mcmouse88.multimodulefeature.core.common

import kotlinx.coroutines.CoroutineScope

object Core {

    private lateinit var coreProvider: CoreProvider

    val commonUi: CommonUi get() = coreProvider.commonUi

    val resources: Resources get() = coreProvider.resources

    val logger: Logger get() = coreProvider.logger

    val globalScope: CoroutineScope get() = coreProvider.globalScope

    val errorHandler: ErrorHandler get() = coreProvider.errorHandler

    val appRestarter: AppRestarter get() = coreProvider.appRestarted

    val screenCommunication: ScreenCommunication get() = coreProvider.screenCommunication

    val localTimeoutMillis: Long get() = coreProvider.localTimeoutMillis

    val remoteTimeoutMillis: Long get() = coreProvider.remoteTimeoutMillis

    val debouncePeriodMillis: Long get() = coreProvider.debouncePeriodMillis

    fun init(coreProvider: CoreProvider) {
        this.coreProvider = coreProvider
    }
}