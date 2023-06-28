package com.mcmouse88.multimodulefeature

import android.app.Application
import com.mcmouse88.multimodulefeature.core.common.Core
import com.mcmouse88.multimodulefeature.core.common.CoreProvider
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject lateinit var coreProvider: CoreProvider

    override fun onCreate() {
        super.onCreate()
        Core.init(coreProvider)
    }
}