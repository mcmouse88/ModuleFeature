package com.mcmouse88.multimodulefeature.cart.presentation.quantity

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mcmouse88.multimodulefeature.cart.R
import com.mcmouse88.multimodulefeature.cart.databinding.DialogQuantityBinding
import com.mcmouse88.multimodulefeature.core.presentation.BaseScreen
import com.mcmouse88.multimodulefeature.core.presentation.args
import com.mcmouse88.multimodulefeature.core.presentation.live.observeEvent
import com.mcmouse88.multimodulefeature.core.presentation.viewModelCreator
import com.mcmouse88.multimodulefeature.core.presentation.views.observe
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EditQuantityDialogFragment : DialogFragment() {

    class Screen(
        val cartItemId: Long,
        val initialQuantity: Int
    ) : BaseScreen

    @Inject
    lateinit var factory: EditQuantityViewModel.Factory
    private val viewModel by viewModelCreator { factory.create(args()) }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setNegativeButton(getString(R.string.cart_action_cancel), null)
            .setPositiveButton(getString(R.string.cart_action_save), null)
            .setTitle(R.string.cart_edit_quantity)
            .setView(R.layout.dialog_quantity)
            .create()
        dialog.setOnShowListener {
            val view = dialog.findViewById<View>(R.id.dialog_root)!!
            val binding = DialogQuantityBinding.bind(view)
            with(binding) {
                if (savedInstanceState == null) {
                    observeInitialQuantity(dialog)
                }
                observeState(dialog)
                setupListeners()
            }
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val quantityInput = binding.etQuantity.text.toString()
                viewModel.saveNewQuantity(quantityInput)
            }
        }
        return dialog
    }

    private fun DialogQuantityBinding.observeState(lifecycleOwner: LifecycleOwner) {
        root.observe(lifecycleOwner, viewModel.stateLiveValue) { state ->
            tvHint.text = getString(R.string.cart_max_quantity, state.maxQuantity)
        }
    }

    private fun DialogQuantityBinding.observeInitialQuantity(lifecycleOwner: LifecycleOwner) {
        viewModel.initialQuantityLiveEvent.observeEvent(lifecycleOwner) {
            etQuantity.setText(it.toString())
        }
    }

    private fun DialogQuantityBinding.setupListeners() {
        root.setTryAgainListener { viewModel.load() }
    }
}