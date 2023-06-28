package com.mcmouse88.multimodulefeature.glue.signin.repositories

import com.mcmouse88.multimodulefeature.core.common.AuthException
import com.mcmouse88.multimodulefeature.core.common.unwrapFirst
import com.mcmouse88.multimodulefeature.data.AccountsDataRepository
import com.mcmouse88.multimodulefeature.sign_in.domain.repositories.ProfileRepository
import javax.inject.Inject

class AdapterProfileRepository @Inject constructor(
    private val accountsDataRepository: AccountsDataRepository
) : ProfileRepository {

    override suspend fun canLoadProfile(): Boolean {
        return try {
            accountsDataRepository.getAccount().unwrapFirst()
            true
        } catch (ignored: AuthException) {
            false
        }
    }
}