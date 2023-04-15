package com.mcmouse88.multimodulefeature.catalog.presentation.details

import com.mcmouse88.multimodulefeature.catalog.R
import com.mcmouse88.multimodulefeature.catalog.domain.entities.ProductWithCartInfo
import com.mcmouse88.multimodulefeature.catalog.domain.usecases.AddToCartUseCase
import com.mcmouse88.multimodulefeature.catalog.domain.usecases.GetProductDetailsUseCase
import com.mcmouse88.multimodulefeature.catalog.presentation.details.ProductDetailFragment.Screen
import com.mcmouse88.multimodulefeature.core.common.Container
import com.mcmouse88.multimodulefeature.core.presentation.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class ProductDetailViewModel @AssistedInject constructor(
    @Assisted private val screen: Screen,
    private val getProductDetailsUseCase: GetProductDetailsUseCase,
    private val addToCartUseCase: AddToCartUseCase
) : BaseViewModel() {

    private val addToCartInProgressFlow = MutableStateFlow(false)
    private val productFlow = getProductDetailsUseCase.getProduct(screen.productId)

    val stateLiveValue = combine(productFlow, addToCartInProgressFlow, ::merge)
        .toLiveValue(Container.Pending)

    fun reload() = debounce {
        getProductDetailsUseCase.reload()
    }

    fun addToCart() = debounce {
        viewModelScope.launch {
            val state = stateLiveValue.value.getOrNull() ?: return@launch
            try {
                addToCartInProgressFlow.value = true
                addToCartUseCase.addToCart(state.product)
            } finally {
                addToCartInProgressFlow.value = false
            }
        }
    }

    private fun merge(
        productContainer: Container<ProductWithCartInfo>,
        isAddToCartInProgress: Boolean
    ): Container<State> {
        return productContainer.map { productWithCartInfo ->
            State(
                productWithCartInfo = productWithCartInfo,
                addToCartInProgress = isAddToCartInProgress
            )
        }
    }

    class State(
        private val productWithCartInfo: ProductWithCartInfo,
        private val addToCartInProgress: Boolean
    ) {
        val product = productWithCartInfo.product
        val showAddToCartProgress: Boolean get() = addToCartInProgress
        val showAddToCartButton: Boolean get() = addToCartInProgress.not()
        val enableAddToCartButton: Boolean get() = productWithCartInfo.isInCart.not()
        val addToCartTextRes: Int
            get() = if (productWithCartInfo.isInCart) {
                R.string.catalog_in_cart
            } else {
                R.string.catalog_add_to_cart
            }
    }

    @AssistedFactory
    interface Factory {
        fun create(screen: Screen): ProductDetailViewModel
    }
}