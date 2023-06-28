package com.mcmouse88.multimodulefeature.glue.navigation.repositories

import com.mcmouse88.multimodulefeature.core.common.Container
import com.mcmouse88.multimodulefeature.data.AccountsDataRepository
import com.mcmouse88.multimodulefeature.navigation.domain.repositories.GetCurrentUsernameRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AdapterGetCurrentUsernameRepository @Inject constructor(
    private val accountsRepository: AccountsDataRepository
) : GetCurrentUsernameRepository {

    override fun getCurrentUsername(): Flow<Container<String>> {
        return accountsRepository.getAccount().map { container ->
            container.map { it.username }
        }
    }

    override fun reload() {
        accountsRepository.reload()
    }
}