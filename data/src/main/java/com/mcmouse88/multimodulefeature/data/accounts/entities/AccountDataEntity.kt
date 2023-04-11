package com.mcmouse88.multimodulefeature.data.accounts.entities

data class AccountDataEntity(
    val id: Long,
    val email: String,
    val username: String,
    val createdAtMillis: Long
)
