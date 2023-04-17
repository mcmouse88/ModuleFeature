package com.mcmouse88.multimodulefeature.orders.presentation.orders

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.elveum.elementadapter.ElementListAdapter
import com.elveum.elementadapter.simpleAdapter
import com.mcmouse88.multimodulefeature.core.presentation.viewBinding
import com.mcmouse88.multimodulefeature.core.presentation.views.observe
import com.mcmouse88.multimodulefeature.core.presentation.views.setupSimpleList
import com.mcmouse88.multimodulefeature.orders.R
import com.mcmouse88.multimodulefeature.orders.databinding.FragmentOrderListBinding
import com.mcmouse88.multimodulefeature.orders.databinding.ItemOrderBinding
import com.mcmouse88.multimodulefeature.orders.databinding.ItemOrderProductBinding
import com.mcmouse88.multimodulefeature.orders.domain.entities.OrderItem
import com.mcmouse88.multimodulefeature.orders.domain.entities.OrderStatus
import com.mcmouse88.multimodulefeature.orders.presentation.orders.entities.UiOrder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderListFragment : Fragment(R.layout.fragment_order_list) {

    private val binding by viewBinding<FragmentOrderListBinding>()
    private val viewModel by viewModels<OrderListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            val adapter = setupList()
            observeState(adapter)
            setupListeners()
        }
    }

    private fun FragmentOrderListBinding.observeState(adapter: ElementListAdapter<UiOrder>) {
        root.observe(viewLifecycleOwner, viewModel.stateLiveValue) { state ->
            adapter.submitList(state.orders)
        }
    }

    private fun FragmentOrderListBinding.setupList(): ElementListAdapter<UiOrder> {
        val adapter = createOrdersAdapter()
        rvOrders.adapter = adapter
        rvOrders.setupSimpleList()
        return adapter
    }

    private fun FragmentOrderListBinding.setupListeners() {
        root.setTryAgainListener { viewModel.reload() }
    }

    private fun makeCreatedAtText(order: UiOrder): CharSequence {
        val text = getString(R.string.orders_created_at, order.createdAt)
        return HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_COMPACT)
    }

    private fun makeRecipientText(order: UiOrder): CharSequence {
        val text = getString(R.string.orders_recipient, order.recipient)
        return HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_COMPACT)
    }

    private fun TextView.renderStatus(orderStatus: OrderStatus) {
        text = orderStatus.name.lowercase().replaceFirstChar { it.titlecaseChar() }
        val color = when (orderStatus) {
            OrderStatus.CREATED -> Color.rgb(0, 128, 192)
            OrderStatus.ACCEPTED -> Color.rgb(192, 128, 32)
            OrderStatus.DELIVERING -> Color.rgb(64, 32, 192)
            OrderStatus.DONE -> Color.rgb(16, 128, 16)
            OrderStatus.CANCELLED -> Color.rgb(128, 0, 0)
        }
        setTextColor(color)
        val drawable = GradientDrawable()
        drawable.setColor(Color.argb(
            32, Color.red(color), Color.green(color), Color.blue(color)
        ))
        drawable.cornerRadius = resources.getDimensionPixelSize(R.dimen.status_radius).toFloat()
        background = drawable
    }

    @Suppress("UNCHECKED_CAST")
    private fun RecyclerView.makeOrderItemsList(order: UiOrder) {
        val adapter = this.adapter as? ElementListAdapter<OrderItem> ?: let {
            setupSimpleList()
            val newAdapter = createOrderItemAdapter()
            this.adapter = newAdapter
            newAdapter
        }
        adapter.submitList(order.orderItems)
    }

    private fun createOrdersAdapter() = simpleAdapter<UiOrder, ItemOrderBinding> {
        areItemsSame = { oldItem, newItem -> oldItem.uuid == newItem.uuid }
        bind {order ->
            tvOrderId.text = getString(R.string.orders_order_id, order.uuid).uppercase()
            tvCreatedAt.text = makeCreatedAtText(order)
            tvRecipient.text = makeRecipientText(order)
            rvOrderItem.makeOrderItemsList(order)
            tvOrderStatus.renderStatus(order.status)

            if (order.canCancel.not()) {
                tvCancel.visibility = View.GONE
            } else {
                if (order.cancelInProgress) {
                    tvCancel.visibility = View.INVISIBLE
                } else {
                    tvCancel.visibility = View.VISIBLE
                }
            }

            pbCancel.isVisible = order.cancelInProgress
        }
        listeners {
            tvCancel.onClick { viewModel::cancelOrder }
        }
    }

    private fun createOrderItemAdapter() = simpleAdapter<OrderItem, ItemOrderProductBinding> {
        areItemsSame = { oldItem, newItem -> oldItem.id == newItem.id }
        bind { cartItem ->
            tvProductName.text = getString(R.string.orders_item_text, cartItem.name, cartItem.quantity)
            tvPrice.text = cartItem.price.text
        }
    }
}