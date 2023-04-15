package com.mcmouse88.multimodulefeature.catalog.presentation

import com.mcmouse88.multimodulefeature.catalog.domain.entities.ProductFilter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface CatalogFilterRouter {

    fun launchFilter(initialFilter: ProductFilter)

    fun sendFilterResults(filter: ProductFilter)

    fun receiveFilterResults(): Flow<ProductFilter>

    fun goBack()

    fun registerBackHandler(scope: CoroutineScope, handler: () -> Boolean)
}