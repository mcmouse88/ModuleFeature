package com.mcmouse88.multimodulefeature.catalog.presentation.catalog

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.elveum.elementadapter.SimpleBindingAdapter
import com.elveum.elementadapter.simpleAdapter
import com.mcmouse88.multimodulefeature.catalog.R
import com.mcmouse88.multimodulefeature.catalog.databinding.FragmentCatalogBinding
import com.mcmouse88.multimodulefeature.catalog.databinding.ItemProductBinding
import com.mcmouse88.multimodulefeature.catalog.domain.entities.ProductWithCartInfo
import com.mcmouse88.multimodulefeature.core.presentation.loadResource
import com.mcmouse88.multimodulefeature.core.presentation.loadUrl
import com.mcmouse88.multimodulefeature.core.presentation.viewBinding
import com.mcmouse88.multimodulefeature.core.presentation.views.observe
import com.mcmouse88.multimodulefeature.core.presentation.views.setupSimpleList
import com.mcmouse88.multimodulefeature.theme.R.drawable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatalogFragment : Fragment(R.layout.fragment_catalog) {

    private val binding by viewBinding<FragmentCatalogBinding>()

    private val viewModel by viewModels<CatalogViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = createAdapter()

        with(binding) {
            observeState(adapter)
            setupList(adapter)
            setupListeners()
        }
    }

    private fun FragmentCatalogBinding.observeState(adapter: SimpleBindingAdapter<ProductWithCartInfo>) {
        root.observe(viewLifecycleOwner, viewModel.stateLiveValue) { state ->
            adapter.submitList(state.products)
        }
    }

    private fun FragmentCatalogBinding.setupList(adapter: SimpleBindingAdapter<ProductWithCartInfo>) {
        rvProducts.setupSimpleList()
        rvProducts.adapter = adapter
    }

    private fun FragmentCatalogBinding.setupListeners() {
        buttonsSortAndFilter.setOnClickListener {
            viewModel.launchFilter()
        }
    }

    private fun createAdapter() = simpleAdapter<ProductWithCartInfo, ItemProductBinding> {
        areItemsSame = { oldItem, newItem -> oldItem.product.id == newItem.product.id }
        areContentsSame = { oldItem, newItem -> oldItem == newItem }

        bind { productWithCartInfo ->
            val product = productWithCartInfo.product
            tvName.text = product.name
            tvShortDescription.text = product.shortDetails
            tvOriginPrice.text = product.price.text
            if (product.priceWithDiscount == null) {
                tvPriceWithDiscount.isVisible = false
                tvOriginPrice.setBackgroundColor(Color.TRANSPARENT)
            } else {
                tvPriceWithDiscount.isVisible = true
                tvPriceWithDiscount.text = product.priceWithDiscount.text
                tvOriginPrice.setBackgroundResource(drawable.core_theme_diagonal_line)
            }

            ivAddToCart.isEnabled = productWithCartInfo.isInCart.not()
            ivAddToCart.setImageResource(
                if (productWithCartInfo.isInCart) drawable.core_theme_ic_done
                else R.drawable.ic_add_to_cart
            )

            if (product.photo.isNotBlank()) {
                ivProduct.loadUrl(product.photo)
            } else {
                ivProduct.loadResource(drawable.core_theme_placeholder)
            }
            tvCategoryHint.text = getString(R.string.catalog_category, product.category)
        }

        listeners {
            ivAddToCart.onClick { productWithCartInfo ->
                viewModel.addToCart(productWithCartInfo)
            }

            root.onClick {  productWithCartInfo ->
                viewModel.launchDetails(productWithCartInfo)
            }
        }
    }
}