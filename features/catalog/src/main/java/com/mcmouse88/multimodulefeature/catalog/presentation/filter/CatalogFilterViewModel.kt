package com.mcmouse88.multimodulefeature.catalog.presentation.filter

import com.mcmouse88.multimodulefeature.catalog.R
import com.mcmouse88.multimodulefeature.catalog.domain.entities.ProductFilter
import com.mcmouse88.multimodulefeature.catalog.domain.entities.SortBy
import com.mcmouse88.multimodulefeature.catalog.domain.entities.SortOrder
import com.mcmouse88.multimodulefeature.catalog.domain.usecases.GetFilterValuesUseCase
import com.mcmouse88.multimodulefeature.catalog.presentation.CatalogFilterRouter
import com.mcmouse88.multimodulefeature.catalog.presentation.filter.CatalogFilterFragment.Screen
import com.mcmouse88.multimodulefeature.core.common.AlertDialogConfig
import com.mcmouse88.multimodulefeature.core.common.Container
import com.mcmouse88.multimodulefeature.core.common.entities.OnChange
import com.mcmouse88.multimodulefeature.core.presentation.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CatalogFilterViewModel @AssistedInject constructor(
    @Assisted private val screen: Screen,
    private val getFilterValuesUseCase: GetFilterValuesUseCase,
    private val router: CatalogFilterRouter
) : BaseViewModel() {

    private val filterFlow = MutableStateFlow(OnChange(screen.filter))
    val stateLiveValue = liveValue<Container<List<FilterItem>>>(Container.Pending)

    private var retainItem: FilterItem? = null
    private var forceExit: Boolean = false

    private var filter: ProductFilter
        get() = filterFlow.value.value
        set(value) {
            filterFlow.value = OnChange(value)
        }

    init {
        viewModelScope.launch {
            filterFlow.collectLatest {
                try {
                    stateLiveValue.value = Container.Success(buildFilterList())
                } catch (e: Exception) {
                    stateLiveValue.value = Container.Error(e)
                }
            }
        }
    }

    fun initBackListener(lifeCycleScope: CoroutineScope) {
        router.registerBackHandler(lifeCycleScope, ::handleBackPressed)
    }

    fun load() = debounce {
        stateLiveValue.value = Container.Pending
        filter = filter
    }

    fun applyFilter() = debounce {
        forceExit = true
        router.sendFilterResults(filter)
        router.goBack()
    }

    private suspend fun buildFilterList(): List<FilterItem> {
        val currentFilter = this.filter
        val minPossiblePrice = getFilterValuesUseCase.getMinPrice()
        val maxPossiblePrice = getFilterValuesUseCase.getMaxPrice()
        val list = mutableListOf<FilterItem>(
            FilterItem.Hint(resources.getString(R.string.catalog_price)),
            FilterItem.RangeSlider(
                minPossiblePrice = minPossiblePrice,
                maxPossiblePrice = minPossiblePrice,
                minPrice = currentFilter.minPrice ?: minPossiblePrice,
                maxPrice = currentFilter.maxPrice ?: maxPossiblePrice,
                listener = { item, min, max ->
                    item.minPrice = min
                    item.maxPrice = max
                    retainItem = item
                    filter = filter.copy(
                        minPrice = min,
                        maxPrice = max
                    )
                }
            ),

            FilterItem.Hint(resources.getString(R.string.catalog_categories)),
            FilterItem.RadioButton(
                text = resources.getString(R.string.catalog_all),
                isChecked = currentFilter.category == null,
                listener = {
                    filter = filter.copy(category = null)
                }
            ),
            *getFilterValuesUseCase.getCategories().map {
                FilterItem.RadioButton(
                    text = it,
                    isChecked = currentFilter.category == it,
                    listener = {
                        filter = filter.copy(category = it)
                    }
                )
            }.toTypedArray(),

            FilterItem.Hint(resources.getString(R.string.catalog_sort_by)),
            *SortBy.values().map {
                FilterItem.RadioButton(
                    isChecked = currentFilter.sortBy == it,
                    text = getSortByName(it),
                    listener = {
                        filter = filter.copy(sortBy = it)
                    }
                )
            }.toTypedArray(),

            FilterItem.Hint(resources.getString(R.string.catalog_sort_order)),
            *SortOrder.values().map {
                FilterItem.RadioButton(
                    isChecked = currentFilter.sortOrder == it,
                    text = getSortOrderName(it),
                    listener = {
                        filter = filter.copy(sortOrder = it)
                    }
                )
            }.toTypedArray(),

            FilterItem.ApplyButton
        )

        val index = list.indexOf(retainItem)
        if (index != -1) {
            list[index] = requireNotNull(retainItem)
        }

        return list
    }

    private fun getSortByName(sortBy: SortBy): String {
        return when (sortBy) {
            SortBy.NAME -> resources.getString(R.string.catalog_sort_name)
            SortBy.PRICE -> resources.getString(R.string.catalog_sort_price)
        }
    }

    private fun getSortOrderName(sortOrder: SortOrder): String {
        return when (sortOrder) {
            SortOrder.ASC -> resources.getString(R.string.catalog_sort_order_asc)
            SortOrder.DESC -> resources.getString(R.string.catalog_sort_order_desc)
        }
    }

    private fun handleBackPressed(): Boolean {
        if (forceExit) return false
        return if (screen.filter != filter) {
            showConfirmExitDialog()
            true
        } else {
            false
        }
    }

    private fun showConfirmExitDialog() {
        viewModelScope.launch {
            val confirmed = commonUi.alertDialog(AlertDialogConfig(
                title = resources.getString(R.string.catalog_apply_filter_title),
                message = resources.getString(R.string.catalog_apply_filter_message),
                positiveButton = resources.getString(R.string.catalog_action_apply),
                negativeButton = resources.getString(R.string.catalog_action_cancel)
            ))
            forceExit = true
            if (confirmed) {
                applyFilter()
            } else {
                router.goBack()
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(screen: Screen): CatalogFilterViewModel
    }
}