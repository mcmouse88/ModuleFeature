package com.mcmouse88.multimodulefeature.profile.presentation.profile

import com.mcmouse88.multimodulefeature.core.common.Container
import com.mcmouse88.multimodulefeature.core.presentation.BaseViewModel
import com.mcmouse88.multimodulefeature.profile.domain.usecases.GetProfileUseCase
import com.mcmouse88.multimodulefeature.profile.domain.usecases.LogoutUseCase
import com.mcmouse88.multimodulefeature.profile.presentation.ProfileRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val router: ProfileRouter
) : BaseViewModel() {

    val profileLiveValue = getProfileUseCase.getProfile()
        .toLiveValue(Container.Pending)

    fun reload() = debounce {
        getProfileUseCase.reload()
    }

    fun logout() = debounce {
        viewModelScope.launch {
            logoutUseCase.logout()
            router.restartApp()
        }
    }

    fun editUsername() = debounce {
        router.launchEditUsername()
    }
}