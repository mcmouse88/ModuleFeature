package com.mcmouse88.multimodulefeature.sign_up.domain.exceptions

import com.mcmouse88.multimodulefeature.core.common.AppException
import com.mcmouse88.multimodulefeature.sign_up.domain.entities.SignUpField

class EmptyFieldException(
    val field: SignUpField
) : AppException()