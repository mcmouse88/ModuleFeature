package com.mcmouse88.multimodulefeature.core.impl

import androidx.fragment.app.FragmentActivity

interface ActivityRequired {

    fun onCreate(activity: FragmentActivity)

    fun onStarted()

    fun onStopped()

    fun onDestroyed()
}