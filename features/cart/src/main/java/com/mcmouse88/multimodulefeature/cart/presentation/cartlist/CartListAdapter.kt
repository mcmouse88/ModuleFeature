package com.mcmouse88.multimodulefeature.cart.presentation.cartlist

import android.graphics.Color
import android.widget.ImageView
import androidx.core.view.isVisible
import com.elveum.elementadapter.setTintColor
import com.elveum.elementadapter.simpleAdapter
import com.mcmouse88.multimodulefeature.cart.databinding.ItemCartProductBinding
import com.mcmouse88.multimodulefeature.cart.presentation.cartlist.entities.UiCartItem
import com.mcmouse88.multimodulefeature.core.presentation.loadUrl
import com.mcmouse88.multimodulefeature.theme.R.color
import com.mcmouse88.multimodulefeature.theme.R.drawable

interface CartAdapterListener {
    fun onIncrement(cartItem: UiCartItem)
    fun onDecrement(cartItem: UiCartItem)
    fun onChangeQuantity(cartItem: UiCartItem)
    fun onChosen(cartItem: UiCartItem)
    fun onToggle(cartItem: UiCartItem)
}

fun createCartAdapter(
    listener: CartAdapterListener
) = simpleAdapter<UiCartItem, ItemCartProductBinding> {

    areItemsSame = { oldItem, newItem -> oldItem.id == newItem.id }
    areContentsSame = { oldItem, newItem -> oldItem == newItem }

    bind { cartItem ->
        tvName.text = cartItem.name
        ivProduct.loadUrl(cartItem.imageUrl)
        tvQuantity.text = cartItem.quantity.toString()
        ivDecrement.setEnabledAndTint(cartItem.canDecrement)
        ivIncrement.setEnabledAndTint(cartItem.canIncrement)
        tvOriginalPrice.text = cartItem.totalOriginPrice.text
        tvPriceWithDiscount.text = cartItem.totalDiscountPrice.text
        checkbox.isVisible = cartItem.showCheckbox
        checkbox.isChecked = cartItem.isChecked
        if (cartItem.totalOriginPrice == cartItem.totalDiscountPrice) {
            tvPriceWithDiscount.isVisible = false
            tvOriginalPrice.setBackgroundColor(Color.TRANSPARENT)
        } else {
            tvPriceWithDiscount.isVisible = true
            tvOriginalPrice.setBackgroundResource(drawable.core_theme_diagonal_line)
        }
    }

    listeners {
        ivIncrement.onClick { listener.onIncrement(it) }
        ivDecrement.onClick { listener.onDecrement(it) }
        tvQuantity.onClick { listener.onChangeQuantity(it) }
        root.onClick { listener.onChosen(it) }
        root.onLongClick {
            listener.onToggle(it)
            true
        }
        checkbox.onClick { listener.onToggle(it) }
    }
}

private fun ImageView.setEnabledAndTint(isEnabled: Boolean) {
    this.isEnabled = isEnabled
    if (isEnabled) {
        this.setTintColor(color.action)
    } else {
        this.setTintColor(color.action_disabled)
    }
}