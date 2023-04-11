package com.mcmouse88.multimodulefeature.data.orders.entities

data class CreateOrderDataEntity(
    val items: List<CreateOrderItemDataEntity>,
    val recipientDataEntity: RecipientDataEntity
)