package com.mcmouse88.multimodulefeature.orders.presentation.orders

import com.mcmouse88.multimodulefeature.core.common.Container
import com.mcmouse88.multimodulefeature.core.common.entities.OnChange
import com.mcmouse88.multimodulefeature.core.presentation.BaseViewModel
import com.mcmouse88.multimodulefeature.orders.domain.entities.Order
import com.mcmouse88.multimodulefeature.orders.domain.usecases.CancelOrderUseCase
import com.mcmouse88.multimodulefeature.orders.domain.usecases.GetOrdersUseCase
import com.mcmouse88.multimodulefeature.orders.presentation.orders.entities.UiOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderListViewModel @Inject constructor(
    private val getOrderUseCase: GetOrdersUseCase,
    private val cancelOrderUseCase: CancelOrderUseCase
) : BaseViewModel() {

    private val cancellationUuidsFlow = MutableStateFlow(OnChange(mutableSetOf<String>()))

    val stateLiveValue = combine(getOrderUseCase.getOrders(), cancellationUuidsFlow, ::merge)
        .toLiveValue()

    fun reload() = debounce {
        getOrderUseCase.reload()
    }

    fun cancelOrder(order: UiOrder) = debounce {
        viewModelScope.launch {
            val cancellationIds = cancellationUuidsFlow.value.value
            cancellationIds.add(order.uuid)
            cancellationUuidsFlow.value = OnChange(cancellationIds)
            try {
                cancelOrderUseCase.cancelOrder(order.origin)
                cancellationIds.remove(order.uuid)
            } catch (e: Exception) {
                cancellationIds.remove(order.uuid)
                cancellationUuidsFlow.value = OnChange(cancellationIds)
                throw e
            }
        }
    }

    private fun merge(
        ordersContainer: Container<List<Order>>,
        cancellations: OnChange<MutableSet<String>>
    ): Container<State> {
        return ordersContainer.map { list ->
            State(
                orders = list.map {
                    UiOrder(
                        origin = it,
                        canCancel = cancelOrderUseCase.canCancel(it),
                        cancelInProgress = cancellations.value.contains(it.uuid)
                    )
                }
            )
        }
    }

    class State(
        val orders: List<UiOrder>
    )
}