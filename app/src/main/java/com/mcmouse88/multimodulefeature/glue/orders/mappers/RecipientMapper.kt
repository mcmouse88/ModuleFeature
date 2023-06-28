package com.mcmouse88.multimodulefeature.glue.orders.mappers

import com.mcmouse88.multimodulefeature.data.orders.entities.RecipientDataEntity
import com.mcmouse88.multimodulefeature.orders.domain.entities.Recipient
import javax.inject.Inject

class RecipientMapper @Inject constructor() {

    fun toRecipientDataEntity(recipient: Recipient): RecipientDataEntity {
        return RecipientDataEntity(
            firstname = recipient.firstName,
            lastname = recipient.lastName,
            address = recipient.address
        )
    }
}