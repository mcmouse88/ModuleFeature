package com.mcmouse88.multimodulefeature.formatters

interface PriceFormatter {
    fun formatPrice(usdCents: Int): String
}