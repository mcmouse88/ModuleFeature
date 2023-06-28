package com.mcmouse88.multimodulefeature.glue.orders.entities

import com.mcmouse88.multimodulefeature.formatters.PriceFormatter
import com.mcmouse88.multimodulefeature.orders.domain.entities.Price

class OrderUsdPrice(
    val usdCents: Int,
    private val formatter: PriceFormatter
) : Price {

    override val text: String
        get() = formatter.formatPrice(usdCents)

    override fun plus(price: Price): Price {
        return OrderUsdPrice(
            usdCents = usdCents + (price as OrderUsdPrice).usdCents,
            formatter = formatter
        )
    }
}