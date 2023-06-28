package com.mcmouse88.multimodulefeature.glue.cart.factories

import com.mcmouse88.multimodulefeature.cart.domain.entities.Price
import com.mcmouse88.multimodulefeature.cart.domain.factories.PriceFactory
import com.mcmouse88.multimodulefeature.formatters.PriceFormatter
import com.mcmouse88.multimodulefeature.glue.cart.entities.CartUsdPrice
import javax.inject.Inject

class DefaultCartPriceFactories @Inject constructor(
    private val priceFormatter: PriceFormatter
): PriceFactory {

    override val zero: Price
        get() = CartUsdPrice(0, priceFormatter)
}