package com.mcmouse88.multimodulefeature.profile.presentation.edit

import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mcmouse88.multimodulefeature.core.presentation.live.observeEvent
import com.mcmouse88.multimodulefeature.core.presentation.viewBinding
import com.mcmouse88.multimodulefeature.core.presentation.views.observe
import com.mcmouse88.multimodulefeature.profile.R
import com.mcmouse88.multimodulefeature.profile.databinding.FragmentEditProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {

    private val binding by viewBinding<FragmentEditProfileBinding>()
    private val viewModel by viewModels<EditProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            observeEvent(savedInstanceState)
            setupListeners()
            observeState()
        }
    }

    private fun FragmentEditProfileBinding.observeEvent(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            viewModel.initialUsernameLiveEvent.observeEvent(viewLifecycleOwner) {
                etUsername.setText(it)
            }
        }
    }

    private fun FragmentEditProfileBinding.setupListeners() {
        btnSave.setOnClickListener {
            viewModel.saveUsername(etUsername.text.toString())
        }
        btnCancel.setOnClickListener { viewModel.goBack() }
        root.setTryAgainListener { viewModel.load() }
    }

    private fun FragmentEditProfileBinding.observeState() {
        root.observe(viewLifecycleOwner, viewModel.stateLiveValue) { state ->
            btnSave.isEnabled = state.enableSaveButton
            progressBar.isInvisible = state.showProgress.not()
        }
    }
}