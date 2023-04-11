package com.mcmouse88.multimodulefeature.data.accounts

import com.mcmouse88.multimodulefeature.core.common.AuthException
import com.mcmouse88.multimodulefeature.core.common.Container
import com.mcmouse88.multimodulefeature.core.common.flow.LazyFlowSubjectFactory
import com.mcmouse88.multimodulefeature.data.AccountsDataRepository
import com.mcmouse88.multimodulefeature.data.accounts.entities.AccountDataEntity
import com.mcmouse88.multimodulefeature.data.accounts.entities.SignUpDataEntity
import com.mcmouse88.multimodulefeature.data.accounts.sources.AccountsDataSource
import com.mcmouse88.multimodulefeature.data.settings.SettingsDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RealAccountsDataRepository @Inject constructor(
    private val accountsDataSource: AccountsDataSource,
    private val settingsDataSource: SettingsDataSource,
    coroutineScope: CoroutineScope,
    lazyFlowSubject: LazyFlowSubjectFactory
) : AccountsDataRepository {

    private val accountLazyFlowSubject = lazyFlowSubject.create { accountsDataSource.getAccount() }

    init {
        coroutineScope.launch {
            settingsDataSource.listenToken().collect {
                if (it != null) {
                    accountLazyFlowSubject.newAsyncLoad(silently = true)
                } else {
                    accountLazyFlowSubject.updateWith(Container.Error(AuthException()))
                }
            }
        }
    }

    override fun getAccount(): Flow<Container<AccountDataEntity>> {
        return accountLazyFlowSubject.listen()
    }

    override suspend fun setAccountUsername(username: String) {
        val newAccount = accountsDataSource.setAccountUsername(username)
        accountLazyFlowSubject.updateWith(Container.Success(newAccount))
    }

    override suspend fun signIn(email: String, password: String): String {
        return accountsDataSource.signIn(email, password)
    }

    override suspend fun signUp(signUpData: SignUpDataEntity) {
        accountsDataSource.signUp(signUpData)
    }

    override fun reload() {
        accountLazyFlowSubject.newAsyncLoad()
    }
}