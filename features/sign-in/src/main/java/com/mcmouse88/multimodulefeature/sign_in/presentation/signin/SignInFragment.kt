package com.mcmouse88.multimodulefeature.sign_in.presentation.signin

import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mcmouse88.multimodulefeature.core.presentation.viewBinding
import com.mcmouse88.multimodulefeature.core.presentation.views.observe
import com.mcmouse88.multimodulefeature.sign_in.R
import com.mcmouse88.multimodulefeature.sign_in.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding<FragmentSignInBinding>()
    private val viewModel by viewModels<SignInViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            setupListeners()
            observeScreenState()
        }
    }

    private fun FragmentSignInBinding.setupListeners() {
        root.setTryAgainListener { viewModel.load() }
        btnSignIn.setOnClickListener {
            viewModel.signIn(etEmail.text.toString(), etPassword.text.toString())
        }

        btnSignUp.setOnClickListener { viewModel.launchSignIn(etEmail.text.toString()) }

        etEmail.addTextChangedListener { viewModel.clearEmailError() }
        etPassword.addTextChangedListener { viewModel.clearPasswordError() }
    }

    private fun FragmentSignInBinding.observeScreenState() {
        root.observe(viewLifecycleOwner, viewModel.stateLiveValue) { state ->
            btnSignIn.isEnabled = state.enableButtons
            btnSignUp.isEnabled = state.enableButtons
            progressBar.isInvisible = state.showProgressBar.not()
            tilEmail.error = state.emailError
            tilPassword.error = state.passwordError
        }
    }
}