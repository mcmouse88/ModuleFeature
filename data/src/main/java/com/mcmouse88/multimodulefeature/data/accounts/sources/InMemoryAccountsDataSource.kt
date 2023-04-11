package com.mcmouse88.multimodulefeature.data.accounts.sources

import com.mcmouse88.multimodulefeature.core.common.AuthException
import com.mcmouse88.multimodulefeature.data.accounts.entities.AccountDataEntity
import com.mcmouse88.multimodulefeature.data.accounts.entities.SignUpDataEntity
import com.mcmouse88.multimodulefeature.data.accounts.exceptions.AccountAlreadyExistDataException
import com.mcmouse88.multimodulefeature.data.settings.SettingsDataSource
import kotlinx.coroutines.delay
import java.util.*
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class InMemoryAccountsDataSource @Inject constructor(
    private val settings: SettingsDataSource
) : AccountsDataSource {

    private val records = mutableListOf(
        Record(
            account = AccountDataEntity(
                id = 1,
                email = "admin@google.com",
                username = "admin",
                createdAtMillis = 0
            ),
            password = "123456"
        )
    )

    override suspend fun signIn(email: String, password: String): String {
        delay(1.seconds)
        val record = records.firstOrNull { it.account.email == email && it.password == password }
            ?: throw AuthException()
        UUID.randomUUID().toString().let {
            record.token = it
            return it
        }
    }

    override suspend fun signUp(signUpData: SignUpDataEntity) {
        delay(1.seconds)
        val record = records.firstOrNull { it.account.email == signUpData.email }
        if (record != null) throw AccountAlreadyExistDataException()
        val newRecord = Record(
            account = AccountDataEntity(
                id = records.size + 1L,
                email = signUpData.email,
                username = signUpData.username,
                createdAtMillis = System.currentTimeMillis()
            ),
            password = signUpData.password
        )
        records.add(newRecord)
    }

    override suspend fun getAccount(): AccountDataEntity {
        val token = settings.getToken() ?: throw AuthException()
        val record = records.firstOrNull { it.token == token } ?: throw AuthException()
        return record.account
    }

    override suspend fun setAccountUsername(username: String): AccountDataEntity {
        val token = settings.getToken() ?: throw AuthException()
        val record = records.firstOrNull { it.token == token } ?: throw AuthException()
        delay(1.seconds)
        record.account = record.account.copy(
            username = username
        )
        return record.account
    }

    private class Record(
        var account: AccountDataEntity,
        var token: String? = null,
        val password: String
    )
}