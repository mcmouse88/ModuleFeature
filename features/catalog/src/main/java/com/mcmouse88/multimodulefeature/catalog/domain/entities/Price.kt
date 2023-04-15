package com.mcmouse88.multimodulefeature.catalog.domain.entities

import java.io.Serializable

interface Price : Serializable {

    val text: String

    operator fun minus(price: Price): Price
    operator fun plus(price: Price): Price
    operator fun times(proportion: Double): Price
    operator fun compareTo(price: Price): Int
    operator fun div(price: Price): Double
}