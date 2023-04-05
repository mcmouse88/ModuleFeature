package com.mcmouse88.multimodulefeature.core.impl

import android.content.Context
import com.mcmouse88.multimodulefeature.core.common.*
import kotlinx.coroutines.CoroutineScope

class DefaultCoreProvider(
    private val appContext: Context,
    override val appRestarted: AppRestarter,
    override val commonUi: CommonUi = AndroidCommonUi(appContext),
    override val resources: Resources = AndroidResources(appContext),
    override val screenCommunication: ScreenCommunication = DefaultScreenCommunication(),
    override val globalScope: CoroutineScope = createDefaultGlobalScope(),
    override val logger: Logger = AndroidLogger(),
    override val errorHandler: ErrorHandler = DefaultErrorHandler(
        logger,
        commonUi,
        resources,
        appRestarted,
        globalScope
    )
) : CoreProvider