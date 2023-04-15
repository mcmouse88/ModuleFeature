package com.mcmouse88.multimodulefeature.catalog.domain.usecases

import com.mcmouse88.multimodulefeature.catalog.domain.entities.ProductFilter
import com.mcmouse88.multimodulefeature.catalog.domain.entities.ProductWithCartInfo
import com.mcmouse88.multimodulefeature.catalog.domain.repositories.CartRepository
import com.mcmouse88.multimodulefeature.catalog.domain.repositories.ProductRepository
import com.mcmouse88.multimodulefeature.core.common.Container
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetCatalogUseCase @Inject constructor(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository
) {
    fun getProducts(filter: ProductFilter): Flow<Container<List<ProductWithCartInfo>>> {
        return combine(
            productRepository.getProducts(filter),
            cartRepository.getProductIdentifiersInCart()
        ) { productsContainer, idsInCartContainer ->
            if (productsContainer !is Container.Success) return@combine productsContainer.map()
            if (idsInCartContainer !is Container.Success) return@combine idsInCartContainer.map()
            val products = productsContainer.value
            val idsInCart = idsInCartContainer.value
            val productsWithCartInfo = products.map { ProductWithCartInfo(it, idsInCart.contains(it.id)) }
            return@combine Container.Success(productsWithCartInfo)
        }
    }
}