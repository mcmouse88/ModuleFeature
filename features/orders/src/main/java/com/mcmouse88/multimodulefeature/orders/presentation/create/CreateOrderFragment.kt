package com.mcmouse88.multimodulefeature.orders.presentation.create

import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.elveum.elementadapter.ElementListAdapter
import com.elveum.elementadapter.simpleAdapter
import com.mcmouse88.multimodulefeature.core.presentation.live.observeEvent
import com.mcmouse88.multimodulefeature.core.presentation.viewBinding
import com.mcmouse88.multimodulefeature.core.presentation.views.observe
import com.mcmouse88.multimodulefeature.core.presentation.views.setupSimpleList
import com.mcmouse88.multimodulefeature.orders.R
import com.mcmouse88.multimodulefeature.orders.databinding.FragmentCreateOrderBinding
import com.mcmouse88.multimodulefeature.orders.databinding.ItemOrderProductBinding
import com.mcmouse88.multimodulefeature.orders.domain.entities.CartItem
import com.mcmouse88.multimodulefeature.orders.domain.entities.Field
import com.mcmouse88.multimodulefeature.orders.domain.entities.Recipient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateOrderFragment : Fragment(R.layout.fragment_create_order) {

    private val binding by viewBinding<FragmentCreateOrderBinding>()
    private val viewModel by viewModels<CreateOrderViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            val adapter = setupOrderItemsList()
            observeState(adapter)
            observeEvent()
            setupListeners()
        }
    }

    private fun FragmentCreateOrderBinding.observeState(adapter: ElementListAdapter<CartItem>) {
        root.observe(viewLifecycleOwner, viewModel.stateLiveValue) { state ->
            btnCreateOrder.isEnabled = state.enableCreateOrderButton
            progressBar.isInvisible = state.showProgressBar.not()
            tvTotalPriceValue.text = state.cart.totalPrice.text
            adapter.submitList(state.cart.cartItems)
        }
    }

    private fun FragmentCreateOrderBinding.observeEvent() {
        val errorMessage = getString(R.string.orders_empty_field)
        viewModel.emptyFieldErrorLiveEvent.observeEvent(viewLifecycleOwner) {
            val input = when (it) {
                Field.FIRST_NAME -> tilFirstName
                Field.LAST_NAME -> tilLastName
                Field.ADDRESS -> tilAddress
            }
            input.isErrorEnabled = true
            input.error = errorMessage
        }
    }

    private fun FragmentCreateOrderBinding.setupListeners() {
        root.setOnClickListener { viewModel.load() }
        btnCreateOrder.setOnClickListener { viewModel.createOrder(makeRecipient()) }
        etFirstName.addTextChangedListener { tilFirstName.error = null }
        etLastName.addTextChangedListener { tilLastName.error = null }
        etAddress.addTextChangedListener { tilAddress.error = null }
    }

    private fun FragmentCreateOrderBinding.setupOrderItemsList(): ElementListAdapter<CartItem> {
        val adapter = createAdapter()
        rvProduct.setupSimpleList()
        rvProduct.adapter = adapter
        return adapter
    }

    private fun createAdapter(): ElementListAdapter<CartItem> = simpleAdapter<CartItem, ItemOrderProductBinding> {
        areItemsSame = { oldItem, newItem -> oldItem.productId == newItem.productId }
        bind { cartItem ->
            tvProductName.text = getString(R.string.orders_item_text, cartItem.name, cartItem.quantity)
            tvPrice.text = cartItem.price.text
        }
    }

    private fun FragmentCreateOrderBinding.makeRecipient(): Recipient {
        return Recipient(
            firstName = etFirstName.text.toString(),
            lastName = etLastName.text.toString(),
            address = etAddress.text.toString(),
            comment = etComment.text.toString()
        )
    }
}