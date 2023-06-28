package com.mcmouse88.multimodulefeature.glue.catalog.mappers

import com.mcmouse88.multimodulefeature.catalog.domain.entities.SortOrder
import com.mcmouse88.multimodulefeature.data.products.entities.SortOrderDataValue
import javax.inject.Inject

class SortOrderMapper @Inject constructor() {

    fun toSortOrderDataValue(sortOrder: SortOrder): SortOrderDataValue {
        return when (sortOrder) {
            SortOrder.ASC -> SortOrderDataValue.ASC
            SortOrder.DESC -> SortOrderDataValue.DESC
        }
    }
}