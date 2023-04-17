package com.mcmouse88.multimodulefeature.orders.domain.entities

data class Recipient(
    val firstName: String,
    val lastName: String,
    val address: String,
    val comment: String
)