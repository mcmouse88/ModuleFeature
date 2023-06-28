package com.mcmouse88.multimodulefeature.formatters

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultDataTimeFormatter @Inject constructor() : DataTimeFormatter {

    private val formatter = SimpleDateFormat.getDateTimeInstance()

    override fun formatDataTime(millis: Long): String {
        return formatter.format(Date(millis))
    }
}