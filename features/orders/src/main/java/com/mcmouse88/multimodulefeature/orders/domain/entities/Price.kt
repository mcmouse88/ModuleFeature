package com.mcmouse88.multimodulefeature.orders.domain.entities

interface Price {
    val text: String

    operator fun plus(price: Price): Price
}