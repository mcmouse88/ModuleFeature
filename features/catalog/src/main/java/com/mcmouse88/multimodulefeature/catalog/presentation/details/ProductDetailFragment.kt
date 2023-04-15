package com.mcmouse88.multimodulefeature.catalog.presentation.details

import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import com.mcmouse88.multimodulefeature.catalog.R
import com.mcmouse88.multimodulefeature.catalog.databinding.FragmentProductDetailBinding
import com.mcmouse88.multimodulefeature.core.presentation.*
import com.mcmouse88.multimodulefeature.core.presentation.views.observe
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductDetailFragment : Fragment(R.layout.fragment_product_detail) {

    class Screen(
        val productId: Long
    ): BaseScreen

    @Inject
    lateinit var factory: ProductDetailViewModel.Factory
    private val binding by viewBinding<FragmentProductDetailBinding>()
    private val viewModel by viewModelCreator { factory.create(args()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            setupListeners()
            observeState()
        }
    }

    private fun FragmentProductDetailBinding.observeState() {
        root.observe(viewLifecycleOwner, viewModel.stateLiveValue) { state ->
            val product = state.product
            ivProduct.loadUrl(product.photo)
            tvProductTitle.text = product.name
            tvDetails.text = product.details
            btnAddToCart.isEnabled = state.enableAddToCartButton
            btnAddToCart.isInvisible = state.showAddToCartButton.not()
            btnAddToCart.setText(state.addToCartTextRes)
            pbAddToCart.isInvisible = state.showAddToCartProgress.not()
        }
    }

    private fun FragmentProductDetailBinding.setupListeners() {
        root.setTryAgainListener { viewModel.reload() }
        btnAddToCart.setOnClickListener { viewModel.addToCart() }
    }
}