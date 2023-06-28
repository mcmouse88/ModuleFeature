package com.mcmouse88.multimodulefeature.glue.catalog

import com.mcmouse88.multimodulefeature.R
import com.mcmouse88.multimodulefeature.catalog.presentation.CatalogRouter
import com.mcmouse88.multimodulefeature.catalog.presentation.details.ProductDetailFragment
import com.mcmouse88.multimodulefeature.navigation.GlobalNavComponentRouter
import javax.inject.Inject

class AdapterCatalogRouter @Inject constructor(
    private val globalNavComponentRouter: GlobalNavComponentRouter
) : CatalogRouter {

    override fun launchDetails(productId: Long) {
        globalNavComponentRouter.launch(
            R.id.productDetailsFragment,
            ProductDetailFragment.Screen(productId)
        )
    }

    override fun launchCreateOrder() {
        globalNavComponentRouter.launch(R.id.createOrderFragment)
    }
}