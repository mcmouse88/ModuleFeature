package com.mcmouse88.multimodulefeature.catalog.domain.usecases

import com.mcmouse88.multimodulefeature.catalog.domain.entities.Price
import com.mcmouse88.multimodulefeature.catalog.domain.repositories.ProductRepository
import javax.inject.Inject

class GetFilterValuesUseCase @Inject constructor(
    private val productsRepository: ProductRepository
) {

    suspend fun getMinPrice(): Price {
        return productsRepository.getMinPossiblePrice()
    }

    suspend fun getMaxPrice(): Price {
        return productsRepository.getMaxPossiblePrice()
    }

    suspend fun getCategories(): List<String> {
        return productsRepository.getAllCategories()
    }
}