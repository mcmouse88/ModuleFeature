package com.mcmouse88.multimodulefeature.glue.orders.factories

import com.mcmouse88.multimodulefeature.formatters.PriceFormatter
import com.mcmouse88.multimodulefeature.glue.orders.entities.OrderUsdPrice
import com.mcmouse88.multimodulefeature.orders.domain.entities.Price
import com.mcmouse88.multimodulefeature.orders.domain.factories.PriceFactory
import javax.inject.Inject

class DefaultOrderPriceFactory @Inject constructor(
    private val priceFormatter: PriceFormatter
) : PriceFactory {

    override val zero: Price
        get() = OrderUsdPrice(usdCents = 0, formatter = priceFormatter)
}