package com.mcmouse88.multimodulefeature.orders.domain.exceptions

import com.mcmouse88.multimodulefeature.core.common.AppException
import com.mcmouse88.multimodulefeature.orders.domain.entities.Field

class EmptyFieldException(
    val field: Field
) : AppException()