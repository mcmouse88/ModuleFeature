package com.mcmouse88.multimodulefeature.sign_in.presentation.signin

import com.mcmouse88.multimodulefeature.core.common.AuthException
import com.mcmouse88.multimodulefeature.core.common.Container
import com.mcmouse88.multimodulefeature.core.presentation.BaseViewModel
import com.mcmouse88.multimodulefeature.sign_in.R
import com.mcmouse88.multimodulefeature.sign_in.domain.exceptions.EmptyEmailException
import com.mcmouse88.multimodulefeature.sign_in.domain.exceptions.EmptyPasswordException
import com.mcmouse88.multimodulefeature.sign_in.domain.usecases.IsSignedInUseCase
import com.mcmouse88.multimodulefeature.sign_in.domain.usecases.SignInUseCase
import com.mcmouse88.multimodulefeature.sign_in.presentation.SignInRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val isSignedInUseCase: IsSignedInUseCase,
    private val signInUseCase: SignInUseCase,
    private val router: SignInRouter
) : BaseViewModel() {

    private val loadScreenStateFlow = MutableStateFlow<Container<Unit>>(Container.Pending)
    private val progressStateFlow = MutableStateFlow(false)
    private val emailErrorStateFlow = MutableStateFlow<String?>(null)
    private val passwordErrorStateFlow = MutableStateFlow<String?>(null)

    val stateLiveValue = combine(
        loadScreenStateFlow,
        progressStateFlow,
        emailErrorStateFlow,
        passwordErrorStateFlow,
        ::merge
    ).toLiveValue(Container.Pending)

    init {
        load()
    }

    fun load() = debounce {
        viewModelScope.launch {
            loadScreenStateFlow.value = Container.Pending
            try {
                if (isSignedInUseCase.isSignedIn()) {
                    router.launchMain()
                } else {
                    loadScreenStateFlow.value = Container.Success(Unit)
                }
            } catch (e: Exception) {
                loadScreenStateFlow.value = Container.Error(e)
            }
        }
    }

    fun signIn(email: String, password: String) = debounce {
        viewModelScope.launch {
            try {
                progressStateFlow.value = true
                signInUseCase.signIn(email, password)
                router.launchMain()
            } catch (e: EmptyEmailException) {
                emailErrorStateFlow.value = resources.getString(R.string.signin_empty_email)
            } catch (e: EmptyPasswordException) {
                passwordErrorStateFlow.value = resources.getString(R.string.signin_empty_password)
            } catch (e: AuthException) {
                showErrorDialog(resources.getString(R.string.signin_invalid_email_or_password))
            } finally {
                progressStateFlow.value = false
            }
        }
    }

    fun launchSignIn(email: String) = debounce {
        router.launchSignUp(email)
    }

    fun clearEmailError() {
        emailErrorStateFlow.value = null
    }

    fun clearPasswordError() {
        passwordErrorStateFlow.value = null
    }

    private fun merge(
        loadContainer: Container<Unit>,
        inProgress: Boolean,
        emailError: String?,
        passwordError: String?
    ): Container<State> {
        return loadContainer.map { State(inProgress, emailError, passwordError) }
    }

    class State(
        private val inProgress: Boolean,
        val emailError: String?,
        val passwordError: String?
    ) {
        val enableButtons: Boolean get() = inProgress.not()
        val showProgressBar: Boolean get() = inProgress
    }
}