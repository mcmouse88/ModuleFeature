package com.mcmouse88.multimodulefeature.glue.catalog.mappers

import com.mcmouse88.multimodulefeature.catalog.domain.entities.ProductFilter
import com.mcmouse88.multimodulefeature.data.products.entities.ProductDataFilter
import com.mcmouse88.multimodulefeature.glue.catalog.entities.CatalogUsdPrice
import javax.inject.Inject

class ProductFilterMapper @Inject constructor(
    private val sortOrderMapper: SortOrderMapper,
    private val sortByMapper: SortByMapper
) {
    fun toProductDataFilter(filter: ProductFilter): ProductDataFilter {
        return ProductDataFilter(
            category = filter.category,
            minPriceUsdCents = (filter.minPrice as? CatalogUsdPrice)?.usdCents,
            maxPriceUsdCents = (filter.maxPrice as? CatalogUsdPrice)?.usdCents,
            sortBy = sortByMapper.toSortByDataValue(filter.sortBy),
            sortOrder = sortOrderMapper.toSortOrderDataValue(filter.sortOrder)
        )
    }
}