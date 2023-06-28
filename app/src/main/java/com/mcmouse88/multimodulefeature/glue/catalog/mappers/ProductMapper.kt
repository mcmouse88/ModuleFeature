package com.mcmouse88.multimodulefeature.glue.catalog.mappers

import com.mcmouse88.multimodulefeature.catalog.domain.entities.Product
import com.mcmouse88.multimodulefeature.data.ProductsDataRepository
import com.mcmouse88.multimodulefeature.data.products.entities.ProductDataEntity
import com.mcmouse88.multimodulefeature.formatters.PriceFormatter
import com.mcmouse88.multimodulefeature.glue.catalog.entities.CatalogUsdPrice
import javax.inject.Inject

class ProductMapper @Inject constructor(
    private val productsDataRepository: ProductsDataRepository,
    private val priceFormatter: PriceFormatter
) {

    suspend fun toProduct(dataEntity: ProductDataEntity): Product {
        val discountPrice = productsDataRepository.getDiscountPriceUsdCentsForEntity(dataEntity)?.let {
            CatalogUsdPrice(it, priceFormatter)
        }
        return Product(
            id = dataEntity.id,
            name = dataEntity.name,
            shortDetails = dataEntity.shortDescription,
            details = dataEntity.description,
            category = dataEntity.category,
            price = CatalogUsdPrice(dataEntity.priceUsdCents, priceFormatter),
            priceWithDiscount = discountPrice,
            photo = dataEntity.imageUrl
        )
    }
}