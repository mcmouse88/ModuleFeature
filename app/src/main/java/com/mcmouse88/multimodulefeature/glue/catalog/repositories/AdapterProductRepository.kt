package com.mcmouse88.multimodulefeature.glue.catalog.repositories

import com.mcmouse88.multimodulefeature.catalog.domain.entities.Price
import com.mcmouse88.multimodulefeature.catalog.domain.entities.Product
import com.mcmouse88.multimodulefeature.catalog.domain.entities.ProductFilter
import com.mcmouse88.multimodulefeature.catalog.domain.repositories.ProductRepository
import com.mcmouse88.multimodulefeature.core.common.Container
import com.mcmouse88.multimodulefeature.data.ProductsDataRepository
import com.mcmouse88.multimodulefeature.formatters.PriceFormatter
import com.mcmouse88.multimodulefeature.glue.catalog.entities.CatalogUsdPrice
import com.mcmouse88.multimodulefeature.glue.catalog.mappers.ProductFilterMapper
import com.mcmouse88.multimodulefeature.glue.catalog.mappers.ProductMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AdapterProductRepository @Inject constructor(
    private val productsDataRepository: ProductsDataRepository,
    private val productMapper: ProductMapper,
    private val productFilterMapper: ProductFilterMapper,
    private val priceFormatter: PriceFormatter
) : ProductRepository {

    override fun getProducts(filter: ProductFilter): Flow<Container<List<Product>>> {
        val dataFilter = productFilterMapper.toProductDataFilter(filter)
        return productsDataRepository.getProducts(dataFilter).map { container ->
            container.suspendMap { list ->
                list.map { dataEntity ->
                    productMapper.toProduct(dataEntity)
                }
            }
        }
    }

    override suspend fun getProduct(id: Long): Product {
        val productDataEntity = productsDataRepository.getProductById(id)
        return productMapper.toProduct(productDataEntity)
    }

    override suspend fun getMinPossiblePrice(): Price {
        return CatalogUsdPrice(productsDataRepository.getMinPriceUsdCents(), priceFormatter)
    }

    override suspend fun getMaxPossiblePrice(): Price {
        return CatalogUsdPrice(productsDataRepository.getMaxPriceUsdCents(), priceFormatter)
    }

    override suspend fun getAllCategories(): List<String> {
        return productsDataRepository.getAllCategories()
    }
}