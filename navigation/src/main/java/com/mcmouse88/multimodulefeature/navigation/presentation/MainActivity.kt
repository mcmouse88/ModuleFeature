package com.mcmouse88.multimodulefeature.navigation.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.mcmouse88.multimodulefeature.core.impl.ActivityRequired
import com.mcmouse88.multimodulefeature.navigation.DestinationsProvider
import com.mcmouse88.multimodulefeature.navigation.R
import com.mcmouse88.multimodulefeature.navigation.databinding.ActivityMainBinding
import com.mcmouse88.multimodulefeature.navigation.presentation.navigation.NavComponentRouter
import com.mcmouse88.multimodulefeature.navigation.presentation.navigation.RouterHolder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), RouterHolder {

    @Inject
    lateinit var navComponentRouterFactory: NavComponentRouter.Factory

    @Inject
    lateinit var destinationsProvider: DestinationsProvider

    @Inject
    lateinit var activityRequiredSet: Set<@JvmSuppressWildcards ActivityRequired>

    private val viewModel by viewModels<MainViewModel>()

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val navComponentRouter by lazy(LazyThreadSafetyMode.NONE) {
        navComponentRouterFactory.create(R.id.fragment_container)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        if (savedInstanceState != null) {
            navComponentRouter.onRestoreInstanceState(savedInstanceState)
        }
        navComponentRouter.addDestinationListener {
            updateCartButtonVisibility()
        }

        navComponentRouter.onCreate()
        if (savedInstanceState == null) {
            navComponentRouter.switchToStack(destinationsProvider.provideStartDestinationId())
        }

        with(binding) {
            observeUsername()
            observeCart()
            setupListener()
        }
        activityRequiredSet.forEach {
            it.onCreated(this)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return (navComponentRouter.onNavigateUp()) || super.onSupportNavigateUp()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        navComponentRouter.onSaveInstanceState(outState)
    }

    override fun onStart() {
        super.onStart()
        activityRequiredSet.forEach { it.onStarted() }
    }

    override fun onStop() {
        super.onStop()
        activityRequiredSet.forEach { it.onStopped() }
    }

    override fun onDestroy() {
        super.onDestroy()
        navComponentRouter.onDestroy()
        activityRequiredSet.forEach { it.onDestroyed() }
    }

    override fun requireRouter(): NavComponentRouter {
        return navComponentRouter
    }

    private fun ActivityMainBinding.observeUsername() {
        viewModel.usernameLiveValue.observe(this@MainActivity) { username ->
            tvUsername.isVisible = username != null
            if (username != null) {
                tvUsername.text = getString(R.string.username, username)
            }
        }
    }

    private fun ActivityMainBinding.observeCart() {
        viewModel.cartLiveValue.observe(this@MainActivity) { cartState ->
            updateCartButtonVisibility()
            tvCartCounter.text = cartState.itemsCountDisplayString
        }
    }

    private fun ActivityMainBinding.setupListener() {
        ivCart.setOnClickListener {
            viewModel.launchCart()
        }
    }

    private fun updateCartButtonVisibility() {
        val showCartIcon = viewModel.cartLiveValue.getValue()?.showCartIcon ?: return
        val isAlreadyAtCart = navComponentRouter.hasDestinationId(
            destinationsProvider.provideCartDestinationId()
        )
        binding.cartIconContainer.isVisible = showCartIcon && isAlreadyAtCart.not()
    }
}