package com.mcmouse88.multimodulefeature.catalog.domain.entities

data class ProductWithCartInfo(
    val product: Product,
    val isInCart: Boolean
)