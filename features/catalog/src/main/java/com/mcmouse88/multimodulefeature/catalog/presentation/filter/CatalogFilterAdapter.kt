package com.mcmouse88.multimodulefeature.catalog.presentation.filter

import com.elveum.elementadapter.adapter
import com.elveum.elementadapter.addBinding
import com.google.android.material.slider.LabelFormatter
import com.mcmouse88.multimodulefeature.catalog.databinding.ItemApplyButtonBinding
import com.mcmouse88.multimodulefeature.catalog.databinding.ItemFilterHintBinding
import com.mcmouse88.multimodulefeature.catalog.databinding.ItemFilterRadioButtonBinding
import com.mcmouse88.multimodulefeature.catalog.databinding.ItemFilterRangeSliderBinding
import com.mcmouse88.multimodulefeature.catalog.domain.entities.Price

sealed class FilterItem {

    data class Hint(
        val text: String
    ) : FilterItem()

    data class RangeSlider(
        var maxPrice: Price,
        var minPrice: Price,
        var maxPossiblePrice: Price,
        var minPossiblePrice: Price,
        val listener: (item: RangeSlider, min: Price, max: Price) -> Unit
    ) : FilterItem() {

        fun toPercentage(price: Price): Float {
            val range = (maxPossiblePrice - minPossiblePrice)
            val portion = price - minPossiblePrice
            return (portion / range * 100).toFloat()
        }

        fun fromPercentage(percentage: Float): Price {
            val range = (maxPossiblePrice - minPossiblePrice)
            return minPossiblePrice + range * (percentage / 100.0)
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            other as RangeSlider
            if (maxPrice != other.maxPrice) return false
            if (minPrice != other.minPrice) return false
            if (maxPossiblePrice != other.maxPossiblePrice) return false
            if (minPossiblePrice != other.minPossiblePrice) return false
            return true
        }

        override fun hashCode(): Int {
            var result = maxPrice.hashCode()
            result = 31 * result + minPrice.hashCode()
            result = 31 * result + maxPossiblePrice.hashCode()
            result = 31 * result + minPossiblePrice.hashCode()
            return result
        }
    }

    class RadioButton(
        val isChecked: Boolean,
        val text: String,
        val listener: () -> Unit
    ) : FilterItem() {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            other as RadioButton
            if (isChecked != other.isChecked) return false
            if (text != other.text) return false
            return false
        }

        override fun hashCode(): Int {
            var result = isChecked.hashCode()
            result = 31 * result + text.hashCode()
            return result
        }
    }

    object ApplyButton : FilterItem()
}

fun createFilterAdapter(onApply: () -> Unit) = adapter<FilterItem> {
    defaultAreItemsSame = { oldItem, newItem -> index(oldItem) == index(newItem) }

    addBinding<FilterItem.Hint, ItemFilterHintBinding> {
        bind {
            root.text = it.text
        }
    }

    addBinding<FilterItem.RadioButton, ItemFilterRadioButtonBinding> {
        bind {
            root.isChecked = it.isChecked
            root.text = it.text
        }

        listeners {
            root.onClick {
                it.listener()
            }
        }
    }

    addBinding<FilterItem.RangeSlider, ItemFilterRangeSliderBinding> {
        bind {
            tvMin.text = it.minPrice.text
            tvMax.text = it.maxPrice.text
            rangeSlider.valueFrom = 0f
            rangeSlider.valueTo = 100f
            rangeSlider.setValues(
                it.toPercentage(it.minPrice),
                it.toPercentage(it.maxPrice)
            )
            rangeSlider.labelBehavior = LabelFormatter.LABEL_GONE
        }

        listeners {
            rangeSlider.onCustomListener {
                rangeSlider.addOnChangeListener { _, value, fromUser ->
                    if (fromUser) {
                        val values = rangeSlider.values
                        val min = item().fromPercentage(values[0])
                        val max = item().fromPercentage(values[1])
                        tvMin.text = min.text
                        tvMax.text = max.text
                        item().listener(item(), min, max)
                    }
                }
            }
        }
    }

    addBinding<FilterItem.ApplyButton, ItemApplyButtonBinding> {
        listeners {
            root.onClick {
                onApply()
            }
        }
    }
}