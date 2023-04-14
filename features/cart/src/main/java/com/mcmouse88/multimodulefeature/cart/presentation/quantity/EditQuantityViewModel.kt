package com.mcmouse88.multimodulefeature.cart.presentation.quantity

import com.mcmouse88.multimodulefeature.cart.R
import com.mcmouse88.multimodulefeature.cart.domain.entities.CartItem
import com.mcmouse88.multimodulefeature.cart.domain.exceptions.QuantityOutOfRangeException
import com.mcmouse88.multimodulefeature.cart.domain.usecases.GetCartUseCase
import com.mcmouse88.multimodulefeature.cart.domain.usecases.ValidateCartItemQuantityUseCase
import com.mcmouse88.multimodulefeature.cart.presentation.CartRouter
import com.mcmouse88.multimodulefeature.cart.presentation.quantity.EditQuantityDialogFragment.Screen
import com.mcmouse88.multimodulefeature.core.common.Container
import com.mcmouse88.multimodulefeature.core.presentation.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class EditQuantityViewModel @AssistedInject constructor(
    @Assisted private val screen: Screen,
    private val router: CartRouter,
    private val getCartUseCase: GetCartUseCase,
    private val validateCartItemQuantityUseCase: ValidateCartItemQuantityUseCase
) : BaseViewModel() {

    val initialQuantityLiveEvent = liveEvent<Int>()
    val stateLiveValue = liveValue<Container<State>>()

    init {
        initialQuantityLiveEvent.publish(screen.initialQuantity)
        load()
    }

    fun load() = debounce {
        loadScreenInto(stateLiveValue) {
            val cartItem = getCartUseCase.getCartById(screen.cartItemId)
            val maxQuantity = validateCartItemQuantityUseCase.getMaxQuantity(cartItem)
            State(cartItem, maxQuantity)
        }
    }

    fun saveNewQuantity(input: String) = debounce {
        val cartItem = stateLiveValue.getValue()?.getOrNull()?.cartItem ?: return@debounce
        val parsedQuantity = try {
            input.toInt()
        } catch (e: Exception) {
            commonUi.toast(resources.getString(R.string.cart_invalid_quantity))
            return@debounce
        }
        viewModelScope.launch {
            try {
                validateCartItemQuantityUseCase.validateNewQuantity(cartItem, parsedQuantity)
                router.sendQuantity(EditQuantityResult(cartItem.id, parsedQuantity))
                router.goBack()
            } catch (e: QuantityOutOfRangeException) {
                commonUi.toast(resources.getString(R.string.cart_quantity_out_of_range))
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(screen: Screen): EditQuantityViewModel
    }

    class State(
        val cartItem: CartItem,
        val maxQuantity: Int
    )
}