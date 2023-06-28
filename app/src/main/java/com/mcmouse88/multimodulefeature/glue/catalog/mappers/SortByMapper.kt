package com.mcmouse88.multimodulefeature.glue.catalog.mappers

import com.mcmouse88.multimodulefeature.catalog.domain.entities.SortBy
import com.mcmouse88.multimodulefeature.data.products.entities.SortByDataValue
import javax.inject.Inject

class SortByMapper @Inject constructor() {

    fun toSortByDataValue(sortBy: SortBy): SortByDataValue {
        return when (sortBy) {
            SortBy.PRICE -> SortByDataValue.PRICE
            SortBy.NAME -> SortByDataValue.NAME
        }
    }
}