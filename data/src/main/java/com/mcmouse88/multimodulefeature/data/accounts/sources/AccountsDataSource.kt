package com.mcmouse88.multimodulefeature.data.accounts.sources

import com.mcmouse88.multimodulefeature.data.accounts.entities.AccountDataEntity
import com.mcmouse88.multimodulefeature.data.accounts.entities.SignUpDataEntity

interface AccountsDataSource {

    suspend fun signIn(email: String, password: String): String

    suspend fun signUp(signUpData: SignUpDataEntity)

    suspend fun getAccount(): AccountDataEntity

    suspend fun setAccountUsername(username: String): AccountDataEntity
}