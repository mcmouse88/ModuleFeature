package com.mcmouse88.multimodulefeature.sign_up.domain.exceptions

import com.mcmouse88.multimodulefeature.core.common.AppException

class AccountAlreadyExistException(
    cause: Throwable? = null
) : AppException(cause = cause)