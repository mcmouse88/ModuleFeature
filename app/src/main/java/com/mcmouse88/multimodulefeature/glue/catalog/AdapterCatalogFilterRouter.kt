package com.mcmouse88.multimodulefeature.glue.catalog

import com.mcmouse88.multimodulefeature.R
import com.mcmouse88.multimodulefeature.catalog.domain.entities.ProductFilter
import com.mcmouse88.multimodulefeature.catalog.presentation.CatalogFilterRouter
import com.mcmouse88.multimodulefeature.catalog.presentation.filter.CatalogFilterFragment
import com.mcmouse88.multimodulefeature.core.common.ScreenCommunication
import com.mcmouse88.multimodulefeature.core.common.listen
import com.mcmouse88.multimodulefeature.navigation.GlobalNavComponentRouter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AdapterCatalogFilterRouter @Inject constructor(
    private val globalNavComponentRouter: GlobalNavComponentRouter,
    private val screenCommunication: ScreenCommunication
) : CatalogFilterRouter {

    override fun launchFilter(initialFilter: ProductFilter) {
        globalNavComponentRouter.launch(
            R.id.catalogFilterFragment,
            CatalogFilterFragment.Screen(initialFilter)
        )
    }

    override fun sendFilterResults(filter: ProductFilter) {
        screenCommunication.publishResult(filter)
    }

    override fun receiveFilterResults(): Flow<ProductFilter> {
        return screenCommunication.listen(ProductFilter::class.java)
    }

    override fun goBack() {
        globalNavComponentRouter.pop()
    }

    override fun registerBackHandler(scope: CoroutineScope, handler: () -> Boolean) {
        globalNavComponentRouter.requireBackHandler(scope, handler)
    }
}