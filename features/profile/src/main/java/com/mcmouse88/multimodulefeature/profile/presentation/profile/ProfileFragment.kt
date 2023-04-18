package com.mcmouse88.multimodulefeature.profile.presentation.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mcmouse88.multimodulefeature.core.presentation.viewBinding
import com.mcmouse88.multimodulefeature.core.presentation.views.observe
import com.mcmouse88.multimodulefeature.profile.R
import com.mcmouse88.multimodulefeature.profile.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding<FragmentProfileBinding>()
    private val viewModel by viewModels<ProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            setupListeners()
            observeProfile()
        }
    }

    private fun FragmentProfileBinding.setupListeners() {
        btnEditProfile.setOnClickListener { viewModel.editUsername() }
        btnLogout.setOnClickListener { viewModel.logout() }
        root.setTryAgainListener { viewModel.reload() }
    }

    private fun FragmentProfileBinding.observeProfile() {
        root.observe(viewLifecycleOwner, viewModel.profileLiveValue) { profile ->
            tvEmail.text = profile.email
            tvUsername.text = profile.username
            tvCreatedAt.text = profile.createdAt
        }
    }
}