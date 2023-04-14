package com.mcmouse88.multimodulefeature.cart.presentation.cartlist

import com.mcmouse88.multimodulefeature.cart.domain.entities.Cart
import com.mcmouse88.multimodulefeature.cart.domain.entities.CartItem
import com.mcmouse88.multimodulefeature.cart.domain.entities.Price
import com.mcmouse88.multimodulefeature.cart.domain.usecases.ChangeCartItemQuantityUseCase
import com.mcmouse88.multimodulefeature.cart.domain.usecases.DeleteCartItemUseCase
import com.mcmouse88.multimodulefeature.cart.domain.usecases.GetCartUseCase
import com.mcmouse88.multimodulefeature.cart.presentation.CartRouter
import com.mcmouse88.multimodulefeature.cart.presentation.cartlist.entities.UiCartItem
import com.mcmouse88.multimodulefeature.core.common.Container
import com.mcmouse88.multimodulefeature.core.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartListViewModel @Inject constructor(
    private val getCartUseCase: GetCartUseCase,
    private val changeCartItemQuantityUseCase: ChangeCartItemQuantityUseCase,
    private val deleteCartItemUseCase: DeleteCartItemUseCase,
    private val router: CartRouter
) : BaseViewModel(), CartAdapterListener {

    private val selectionModeFlow = MutableStateFlow<SelectionMode>(SelectionMode.Disabled)
    val stateLiveValue = combine(
        selectionModeFlow,
        getCartUseCase.getCart(),
        ::merge
    ).toLiveValue()

    init {
        viewModelScope.launch {
            router.receiveQuantity().collect { result ->
                val cartItem = getCartUseCase.getCartById(result.cartId)
                changeCartItemQuantityUseCase.changeQuantity(cartItem, result.quantity)
            }
        }
    }

    override fun onIncrement(cartItem: UiCartItem) {
        viewModelScope.launch {
            changeCartItemQuantityUseCase.incrementQuantity(cartItem.origin)
        }
    }

    override fun onDecrement(cartItem: UiCartItem) {
        viewModelScope.launch {
            changeCartItemQuantityUseCase.decrementQuantity(cartItem.origin)
        }
    }

    override fun onChangeQuantity(cartItem: UiCartItem) {
        router.launchEditQuantity(cartItem.id, cartItem.quantity)
    }

    override fun onChosen(cartItem: UiCartItem) {
        if (selectionModeFlow.value is SelectionMode.Disabled) {
            router.launchProductDetails(cartItem.product.id)
        } else {
            onToggle(cartItem)
        }
    }

    override fun onToggle(cartItem: UiCartItem) {
        val selectionMode = selectionModeFlow.value
        if (selectionMode is SelectionMode.Enabled) {
            val selectedIds = selectionMode.selectedIds
            if (selectedIds.contains(cartItem.id)) {
                selectedIds.remove(cartItem.id)
            } else {
                selectedIds.add(cartItem.id)
            }
            selectionModeFlow.value = SelectionMode.Enabled(selectedIds)
        } else {
            selectionModeFlow.value = SelectionMode.Enabled(mutableSetOf(cartItem.id))
        }
    }

    fun initBackListener(lifecycleScope: CoroutineScope) {
        router.registerBackHandler(lifecycleScope, ::onBackPressed)
    }

    fun reload() = debounce {
        getCartUseCase.reload()
    }

    fun deleteSelected() = debounce {
        val selectionMode = selectionModeFlow.value
        if (selectionMode is SelectionMode.Enabled) {
            viewModelScope.launch {
                val currentState = currentSuccessState() ?: return@launch
                val cartItemsToBeDeleted = currentState.cartItems
                    .filter { selectionMode.selectedIds.contains(it.id) }
                    .map { it.origin }
                deleteCartItemUseCase.deleteCartItems(cartItemsToBeDeleted)
                if (cartItemsToBeDeleted.size == currentState.cartItems.size) {
                    selectionModeFlow.value = SelectionMode.Disabled
                } else {
                    selectionModeFlow.value = SelectionMode.Enabled()
                }
            }
        }
    }

    fun showEditQuantity() = debounce {
        val selectedCartItem = findSelectedCartItem() ?: return@debounce
        router.launchEditQuantity(selectedCartItem.id, selectedCartItem.quantity)
    }

    fun showDetails() = debounce {
        val selectedCartItem = findSelectedCartItem() ?: return@debounce
        router.launchProductDetails(selectedCartItem.product.id)
    }

    fun createOrder() = debounce {
        router.launchCreateOrder()
    }

    private fun onBackPressed(): Boolean {
        if (selectionModeFlow.value is SelectionMode.Enabled) {
            selectionModeFlow.value = SelectionMode.Disabled
            return true
        }
        return false
    }

    private fun findSelectedCartItem(): CartItem? {
        val selectionMode = selectionModeFlow.value
        if (selectionMode !is SelectionMode.Enabled) return null
        if (selectionMode.selectedIds.size != 1) return null
        val state = stateLiveValue.value.getOrNull() ?: return null
        val selectedId = selectionMode.selectedIds.first()
        return state.cartItems.firstOrNull { it.id == selectedId }?.origin
    }

    private fun merge(
        selectionMode: SelectionMode,
        cartContainer: Container<Cart>
    ): Container<State> {
        val showCheckBox = selectionMode is SelectionMode.Enabled
        val countOfSelectedItems = if (selectionMode is SelectionMode.Enabled) {
            selectionMode.selectedIds.size
        } else {
            0
        }
        return cartContainer.map { cart ->
            State(
                totalPrice = cart.totalPrice,
                totalPriceWithDiscount = cart.totalPriceWithDiscount,
                showDeleteAction = countOfSelectedItems > 0,
                showDetailsAction = countOfSelectedItems == 1,
                showEditQuantityAction = countOfSelectedItems == 1,
                enableCreateOrderButton = cart.cartItems.isNotEmpty(),
                cartItems = cart.cartItems.map {
                    val isChecked = selectionMode is SelectionMode.Enabled
                            && selectionMode.selectedIds.contains(it.id)
                    UiCartItem(
                        origin = it,
                        isChecked = isChecked,
                        showCheckbox = showCheckBox,
                        maxQuantity = 1,
                        minQuantity = it.product.totalQuantity
                    )
                }
            )
        }
    }

    private fun currentSuccessState(): State? {
        return (stateLiveValue.value as? Container.Success)?.value
    }

    sealed class SelectionMode {
        object Disabled : SelectionMode()
        class Enabled(
            val selectedIds: MutableSet<Long> = mutableSetOf()
        ) : SelectionMode()
    }

    class State(
        val cartItems: List<UiCartItem>,
        val totalPrice: Price,
        val showDeleteAction: Boolean,
        val showDetailsAction: Boolean,
        val showEditQuantityAction: Boolean,
        val enableCreateOrderButton: Boolean,
        val totalPriceWithDiscount: Price
    ) {
        val showActionsPanel: Boolean
            get() = showDeleteAction || showEditQuantityAction || showDetailsAction
    }
}