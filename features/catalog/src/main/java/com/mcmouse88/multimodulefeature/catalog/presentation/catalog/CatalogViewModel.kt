package com.mcmouse88.multimodulefeature.catalog.presentation.catalog

import com.mcmouse88.multimodulefeature.catalog.domain.entities.ProductFilter
import com.mcmouse88.multimodulefeature.catalog.domain.entities.ProductWithCartInfo
import com.mcmouse88.multimodulefeature.catalog.domain.usecases.AddToCartUseCase
import com.mcmouse88.multimodulefeature.catalog.domain.usecases.GetCatalogUseCase
import com.mcmouse88.multimodulefeature.catalog.presentation.CatalogFilterRouter
import com.mcmouse88.multimodulefeature.catalog.presentation.CatalogRouter
import com.mcmouse88.multimodulefeature.core.common.Container
import com.mcmouse88.multimodulefeature.core.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val getCatalogUseCase: GetCatalogUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val catalogRouter: CatalogRouter,
    private val catalogFilterRouter: CatalogFilterRouter
) : BaseViewModel() {

    private val filterFlow = MutableStateFlow(ProductFilter.EMPTY)

    val stateLiveValue = filterFlow
        .flatMapLatest { filter ->
            getCatalogUseCase.getProducts(filter).map { container ->
                container.map { product ->
                    State(product, filter)
                }
            }
        }.toLiveValue(Container.Pending)

    init {
        viewModelScope.launch {
            catalogFilterRouter.receiveFilterResults().collectLatest {
                filterFlow.value = it
            }
        }
    }

    fun launchFilter() = debounce {
        catalogFilterRouter.launchFilter(filterFlow.value)
    }

    fun launchDetails(productWithCartInfo: ProductWithCartInfo) = debounce {
        catalogRouter.launchDetails(productWithCartInfo.product.id)
    }

    fun addToCart(productWithCartInfo: ProductWithCartInfo) = debounce {
        viewModelScope.launch {
            addToCartUseCase.addToCart(productWithCartInfo.product)
        }
    }

    class State(
        val products: List<ProductWithCartInfo>,
        val filter: ProductFilter
    )
}