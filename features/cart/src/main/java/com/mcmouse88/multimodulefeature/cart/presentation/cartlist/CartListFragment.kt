package com.mcmouse88.multimodulefeature.cart.presentation.cartlist

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.elveum.elementadapter.ElementListAdapter
import com.mcmouse88.multimodulefeature.cart.R
import com.mcmouse88.multimodulefeature.cart.databinding.FragmentCartListBinding
import com.mcmouse88.multimodulefeature.cart.presentation.cartlist.entities.UiCartItem
import com.mcmouse88.multimodulefeature.core.presentation.viewBinding
import com.mcmouse88.multimodulefeature.core.presentation.views.observe
import com.mcmouse88.multimodulefeature.core.presentation.views.setupSimpleList
import com.mcmouse88.multimodulefeature.theme.R.drawable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartListFragment : Fragment(R.layout.fragment_cart_list) {

    private val binding by viewBinding<FragmentCartListBinding>()

    private val viewModel by viewModels<CartListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = createCartAdapter(viewModel)
        with(binding) {
            setupList(adapter)
            setupActions()
            observeState(adapter)
            setupListeners()
        }
        viewModel.initBackListener(viewLifecycleOwner.lifecycleScope)
    }

    private fun FragmentCartListBinding.setupList(adapter: ElementListAdapter<UiCartItem>) {
        rvCart.setupSimpleList()
        rvCart.adapter = adapter
    }

    private fun FragmentCartListBinding.setupActions() {
        actionDelete.ivAction.setImageResource(R.drawable.ic_delete)
        actionDelete.tvAction.setText(R.string.cart_action_delete)

        actionShowDetail.ivAction.setImageResource(R.drawable.ic_detail)
        actionShowDetail.tvAction.setText(R.string.cart_action_details)

        actionEditQuantity.ivAction.setImageResource(R.drawable.ic_edit)
        actionEditQuantity.tvAction.setText(R.string.cart_action_edit)
    }

    private fun FragmentCartListBinding.observeState(adapter: ElementListAdapter<UiCartItem>) {
        root.observe(viewLifecycleOwner, viewModel.stateLiveValue) { state ->
            adapter.submitList(state.cartItems)
            tvTotalPrice.text = state.totalPrice.text
            if (state.totalPriceWithDiscount == state.totalPrice) {
                tvTotalPriceWithDiscountValue.isVisible = false
                tvTotalPriceValue.background = null
            } else {
                tvTotalPriceWithDiscountValue.isVisible = true
                tvTotalPriceWithDiscountValue.text = state.totalPriceWithDiscount.text
                tvTotalPriceValue.setBackgroundResource(drawable.core_theme_diagonal_line)
            }
            btnCreateOrder.isEnabled = state.enableCreateOrderButton
            actionDelete.root.isVisible = state.showDeleteAction
            actionEditQuantity.root.isVisible = state.showEditQuantityAction
            actionShowDetail.root.isVisible = state.showDetailsAction
            actionsContainer.isVisible = state.showActionsPanel
        }
    }

    private fun FragmentCartListBinding.setupListeners() {
        btnCreateOrder.setOnClickListener {
            viewModel.createOrder()
        }

        actionDelete.root.setOnClickListener {
            viewModel.deleteSelected()
        }

        actionEditQuantity.root.setOnClickListener {
            viewModel.showEditQuantity()
        }

        actionShowDetail.root.setOnClickListener {
            viewModel.showDetails()
        }
        root.setTryAgainListener { viewModel.reload() }
    }
}