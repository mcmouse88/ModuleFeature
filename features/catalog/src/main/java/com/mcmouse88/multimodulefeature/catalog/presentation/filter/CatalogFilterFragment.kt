package com.mcmouse88.multimodulefeature.catalog.presentation.filter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.elveum.elementadapter.ElementListAdapter
import com.mcmouse88.multimodulefeature.catalog.R
import com.mcmouse88.multimodulefeature.catalog.databinding.FragmentFilterBinding
import com.mcmouse88.multimodulefeature.catalog.domain.entities.ProductFilter
import com.mcmouse88.multimodulefeature.core.presentation.BaseScreen
import com.mcmouse88.multimodulefeature.core.presentation.args
import com.mcmouse88.multimodulefeature.core.presentation.viewBinding
import com.mcmouse88.multimodulefeature.core.presentation.viewModelCreator
import com.mcmouse88.multimodulefeature.core.presentation.views.observe
import com.mcmouse88.multimodulefeature.core.presentation.views.setupSimpleList
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CatalogFilterFragment : Fragment(R.layout.fragment_filter) {

    class Screen(
        val filter: ProductFilter
    ) : BaseScreen

    private val binding by viewBinding<FragmentFilterBinding>()

    @Inject
    lateinit var factory: CatalogFilterViewModel.Factory
    private val viewModel by viewModelCreator { factory.create(args()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = createFilterAdapter(
            onApply = { viewModel.applyFilter() }
        )

        with(binding) {
            rvFilter.setupSimpleList()
            rvFilter.adapter = adapter
            observeState(adapter)
            setupListener()
        }

        viewModel.initBackListener(viewLifecycleOwner.lifecycleScope)
    }

    private fun FragmentFilterBinding.observeState(adapter: ElementListAdapter<FilterItem>) {
        root.observe(viewLifecycleOwner, viewModel.stateLiveValue, adapter::submitList)
    }

    private fun FragmentFilterBinding.setupListener() {
        root.setTryAgainListener { viewModel.load() }
    }
}