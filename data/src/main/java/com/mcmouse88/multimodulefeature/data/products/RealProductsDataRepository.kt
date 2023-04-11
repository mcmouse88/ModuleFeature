package com.mcmouse88.multimodulefeature.data.products

import com.mcmouse88.multimodulefeature.core.common.Container
import com.mcmouse88.multimodulefeature.core.common.entities.OnChange
import com.mcmouse88.multimodulefeature.core.common.flow.LazyFlowSubjectFactory
import com.mcmouse88.multimodulefeature.data.ProductsDataRepository
import com.mcmouse88.multimodulefeature.data.products.entities.ProductDataEntity
import com.mcmouse88.multimodulefeature.data.products.entities.ProductDataFilter
import com.mcmouse88.multimodulefeature.data.products.sources.ProductsDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class RealProductsDataRepository @Inject constructor(
    private val productsDataSource: ProductsDataSource,
    private val lazyFlowSubjectFactory: LazyFlowSubjectFactory
) : ProductsDataRepository {

    private val updateNotifierFlow = MutableStateFlow(OnChange(Unit))

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getProducts(filter: ProductDataFilter): Flow<Container<List<ProductDataEntity>>> {
        return updateNotifierFlow.flatMapLatest {
            lazyFlowSubjectFactory.create {
                delay(1.seconds)
                productsDataSource.getProducts(filter)
            }.listen()
        }
    }

    override suspend fun changeQuantityBy(id: Long, quantityBy: Int) {
        productsDataSource.changeQuantityBy(id, quantityBy)
        updateNotifierFlow.value = OnChange(Unit)
    }

    override suspend fun getProductById(id: Long): ProductDataEntity {
        return productsDataSource.getProductById(id)
    }

    override suspend fun getMinPriceUsdCents(): Int {
        return productsDataSource.getProducts(ProductDataFilter.DEFAULT).minOf {
            getDiscountPriceUsdCentsForEntity(it) ?: it.priceUsdCents
        }
    }

    override suspend fun getMaxPriceUsdCents(): Int {
        return productsDataSource.getProducts(ProductDataFilter.DEFAULT).maxOf {
            getDiscountPriceUsdCentsForEntity(it) ?: it.priceUsdCents
        }
    }

    override suspend fun getDiscountPriceUsdCentsForEntity(product: ProductDataEntity): Int? {
        return productsDataSource.getDiscountPriceUsdCentsForEntity(product)
    }

    override suspend fun getAllCategories(): List<String> {
        return productsDataSource.getAllCategories()
    }
}