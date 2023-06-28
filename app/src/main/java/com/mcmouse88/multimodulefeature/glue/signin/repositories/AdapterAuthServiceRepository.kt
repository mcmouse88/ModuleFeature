package com.mcmouse88.multimodulefeature.glue.signin.repositories

import com.mcmouse88.multimodulefeature.data.AccountsDataRepository
import com.mcmouse88.multimodulefeature.sign_in.domain.repositories.AuthServiceRepository
import javax.inject.Inject

class AdapterAuthServiceRepository @Inject constructor(
    private val accountsDataRepository: AccountsDataRepository
) : AuthServiceRepository {

    override suspend fun signIn(email: String, password: String): String {
        return accountsDataRepository.signIn(email, password)
    }
}