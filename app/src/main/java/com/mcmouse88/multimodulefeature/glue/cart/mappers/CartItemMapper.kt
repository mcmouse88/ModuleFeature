package com.mcmouse88.multimodulefeature.glue.cart.mappers

import com.mcmouse88.multimodulefeature.cart.domain.entities.CartItem
import com.mcmouse88.multimodulefeature.cart.domain.entities.Product
import com.mcmouse88.multimodulefeature.data.ProductsDataRepository
import com.mcmouse88.multimodulefeature.data.cart.entities.CartItemDataEntity
import com.mcmouse88.multimodulefeature.formatters.PriceFormatter
import com.mcmouse88.multimodulefeature.glue.cart.entities.CartUsdPrice
import javax.inject.Inject

class CartItemMapper @Inject constructor(
    private val productsDataRepository: ProductsDataRepository,
    private val priceFormatter: PriceFormatter
) {

    suspend fun toCartItem(dataEntity: CartItemDataEntity): CartItem {
        val productDataEntity = productsDataRepository.getProductById(dataEntity.id)
        val productPriceWithDiscount = productsDataRepository.getDiscountPriceUsdCentsForEntity(productDataEntity)
        val product = Product(
            id = productDataEntity.id,
            name = productDataEntity.name,
            shortDetails = productDataEntity.shortDescription,
            photo = productDataEntity.imageUrl,
            totalQuantity = productDataEntity.quantityAvailable
        )
        val discountPerItem = productDataEntity.priceUsdCents - (productPriceWithDiscount ?: productDataEntity.priceUsdCents)
        return CartItem(
            id = dataEntity.id,
            product = product,
            quantity = dataEntity.quantity,
            pricePerItem = CartUsdPrice(productDataEntity.priceUsdCents, priceFormatter),
            discountPerItem = CartUsdPrice(discountPerItem, priceFormatter)
        )
    }
}