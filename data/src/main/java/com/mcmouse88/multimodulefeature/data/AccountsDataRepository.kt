package com.mcmouse88.multimodulefeature.data

import com.mcmouse88.multimodulefeature.core.common.Container
import com.mcmouse88.multimodulefeature.data.accounts.entities.AccountDataEntity
import com.mcmouse88.multimodulefeature.data.accounts.entities.SignUpDataEntity
import kotlinx.coroutines.flow.Flow

interface AccountsDataRepository {

    fun getAccount(): Flow<Container<AccountDataEntity>>

    suspend fun setAccountUsername(username: String)

    suspend fun signIn(email: String, password: String): String

    suspend fun signUp(signUpData: SignUpDataEntity)

    fun reload()
}