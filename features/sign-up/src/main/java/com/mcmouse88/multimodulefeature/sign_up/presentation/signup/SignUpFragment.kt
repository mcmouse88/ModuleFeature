package com.mcmouse88.multimodulefeature.sign_up.presentation.signup

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.core.view.isInvisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import com.mcmouse88.multimodulefeature.core.presentation.BaseScreen
import com.mcmouse88.multimodulefeature.core.presentation.args
import com.mcmouse88.multimodulefeature.core.presentation.live.observeEvent
import com.mcmouse88.multimodulefeature.core.presentation.viewBinding
import com.mcmouse88.multimodulefeature.core.presentation.viewModelCreator
import com.mcmouse88.multimodulefeature.sign_up.R
import com.mcmouse88.multimodulefeature.sign_up.databinding.FragmentSignUpBinding
import com.mcmouse88.multimodulefeature.sign_up.domain.entities.SignUpData
import com.mcmouse88.multimodulefeature.sign_up.domain.entities.SignUpField
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    class Screen(
        val email: String
    ) : BaseScreen

    private val binding by viewBinding<FragmentSignUpBinding>()

    @Inject lateinit var factory: SignUpViewModel.Factory
    private val viewModel by viewModelCreator { factory.create(args()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeEvent(savedInstanceState)
        with(binding) {
            observeState()
            setupListeners()
        }
    }

    private fun observeEvent(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            viewModel.initialEmailLiveEventValue.observeEvent(viewLifecycleOwner) {
                binding.etEmail.setText(it)
            }
        }
        viewModel.clearFieldLiveEventValue.observeEvent(viewLifecycleOwner) {
            getEditTextByField(it).text.clear()
        }
        viewModel.focusFieldLiveEventValue.observeEvent(viewLifecycleOwner) {
            getEditTextByField(it).requestFocus()
            getEditTextByField(it).selectAll()
        }
    }

    private fun FragmentSignUpBinding.observeState() {
        viewModel.stateLiveValue.observe(viewLifecycleOwner) { state ->
            progressBar.isInvisible = state.showProgressBar.not()
            btnCreateAccount.isEnabled = state.enableSignUpButton

            cleanUpErrors()
            if (state.fieldErrorMessage != null) {
                val textInput = getTextInputByField(state.fieldErrorMessage.first)
                textInput.error = state.fieldErrorMessage.second
                textInput.isErrorEnabled = true
            }
        }
    }

    private fun FragmentSignUpBinding.setupListeners() {
        btnCreateAccount.setOnClickListener {
            viewModel.signUp(createSignUpData())
        }
        etEmail.addTextChangedListener { viewModel.clearError(SignUpField.EMAIL) }
        etUsername.addTextChangedListener { viewModel.clearError(SignUpField.USERNAME) }
        etPassword.addTextChangedListener { viewModel.clearError(SignUpField.PASSWORD) }
        etRepeatPassword.addTextChangedListener { viewModel.clearError(SignUpField.REPEAT_PASSWORD) }
    }

    private fun FragmentSignUpBinding.createSignUpData(): SignUpData {
        return SignUpData(
            email = etEmail.text.toString(),
            username = etUsername.text.toString(),
            password = etPassword.text.toString(),
            repeatPassword = etRepeatPassword.text.toString()
        )
    }

    private fun FragmentSignUpBinding.cleanUpErrors() {
        tilEmail.error = null
        tilUsername.error = null
        tilPassword.error = null
        tilRepeatPassword.error = null

        tilEmail.isErrorEnabled = false
        tilUsername.isErrorEnabled = false
        tilPassword.isErrorEnabled = false
        tilRepeatPassword.isErrorEnabled = false
    }

    private fun getEditTextByField(field: SignUpField): EditText {
        return when (field) {
            SignUpField.EMAIL -> binding.etEmail
            SignUpField.USERNAME -> binding.etUsername
            SignUpField.PASSWORD -> binding.etPassword
            SignUpField.REPEAT_PASSWORD -> binding.etRepeatPassword
        }
    }

    private fun getTextInputByField(field: SignUpField): TextInputLayout {
        return when (field) {
            SignUpField.EMAIL -> binding.tilEmail
            SignUpField.USERNAME -> binding.tilUsername
            SignUpField.PASSWORD -> binding.tilPassword
            SignUpField.REPEAT_PASSWORD -> binding.tilRepeatPassword
        }
    }
}