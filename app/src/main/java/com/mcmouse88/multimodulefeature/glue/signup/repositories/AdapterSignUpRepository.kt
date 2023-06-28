package com.mcmouse88.multimodulefeature.glue.signup.repositories

import com.mcmouse88.multimodulefeature.data.AccountsDataRepository
import com.mcmouse88.multimodulefeature.data.accounts.entities.SignUpDataEntity
import com.mcmouse88.multimodulefeature.data.accounts.exceptions.AccountAlreadyExistDataException
import com.mcmouse88.multimodulefeature.sign_up.domain.entities.SignUpData
import com.mcmouse88.multimodulefeature.sign_up.domain.exceptions.AccountAlreadyExistException
import com.mcmouse88.multimodulefeature.sign_up.domain.repositories.SignUpRepository
import javax.inject.Inject

class AdapterSignUpRepository @Inject constructor(
    private val accountsRepository: AccountsDataRepository
) : SignUpRepository {

    override suspend fun signUp(signUpData: SignUpData) {
        try {
            accountsRepository.signUp(
                SignUpDataEntity(
                    email = signUpData.email,
                    username = signUpData.username,
                    password = signUpData.password
                )
            )
        } catch (e: AccountAlreadyExistDataException) {
            throw AccountAlreadyExistException(e)
        }
    }
}