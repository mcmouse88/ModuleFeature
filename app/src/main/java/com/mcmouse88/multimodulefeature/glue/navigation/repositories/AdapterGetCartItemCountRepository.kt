package com.mcmouse88.multimodulefeature.glue.navigation.repositories

import com.mcmouse88.multimodulefeature.core.common.Container
import com.mcmouse88.multimodulefeature.data.CartDataRepository
import com.mcmouse88.multimodulefeature.navigation.domain.repositories.GetCartItemsCountsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AdapterGetCartItemCountRepository @Inject constructor(
    private val cartDataRepository: CartDataRepository
) : GetCartItemsCountsRepository {

    override fun getCartItemCount(): Flow<Container<Int>> {
        return cartDataRepository.getCart().map { container ->
            container.map { list -> list.size }
        }
    }
}