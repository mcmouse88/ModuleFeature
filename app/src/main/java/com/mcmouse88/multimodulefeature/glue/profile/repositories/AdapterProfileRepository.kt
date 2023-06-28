package com.mcmouse88.multimodulefeature.glue.profile.repositories

import com.mcmouse88.multimodulefeature.core.common.Container
import com.mcmouse88.multimodulefeature.data.AccountsDataRepository
import com.mcmouse88.multimodulefeature.formatters.DataTimeFormatter
import com.mcmouse88.multimodulefeature.profile.domain.entities.Profile
import com.mcmouse88.multimodulefeature.profile.domain.repositories.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AdapterProfileRepository @Inject constructor(
    private val accountsDataRepository: AccountsDataRepository,
    private val dateTimeFormatter: DataTimeFormatter
) : ProfileRepository {

    override fun getProfile(): Flow<Container<Profile>> {
        return accountsDataRepository.getAccount().map { container ->
            container.map {
                Profile(
                    id = it.id,
                    username = it.username,
                    email = it.email,
                    createdAt = dateTimeFormatter.formatDataTime(it.createdAtMillis)
                )
            }
        }
    }

    override fun reload() {
        accountsDataRepository.reload()
    }

    override suspend fun editUsername(newUsername: String) {
        accountsDataRepository.setAccountUsername(newUsername)
    }
}