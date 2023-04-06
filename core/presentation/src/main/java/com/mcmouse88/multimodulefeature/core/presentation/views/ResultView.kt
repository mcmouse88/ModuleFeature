package com.mcmouse88.multimodulefeature.core.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.children
import androidx.core.view.isVisible
import com.mcmouse88.multimodulefeature.core.common.AuthException
import com.mcmouse88.multimodulefeature.core.common.Container
import com.mcmouse88.multimodulefeature.core.common.Core
import com.mcmouse88.multimodulefeature.core.presentation.R
import com.mcmouse88.multimodulefeature.core.presentation.databinding.CorePresentationPartResultBinding

class ResultView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    var container: Container<*> = Container.Pending
        set(value) {
            field = value
            notifyUpdates()
        }

    private var tryAgainListener: (() -> Unit)? = null

    private val binding: CorePresentationPartResultBinding

    init {
        val inflater = LayoutInflater.from(context)
        binding = CorePresentationPartResultBinding.inflate(inflater, this, false)
        addView(binding.root)

        if (isInEditMode) {
            container = Container.Success("")
        } else {
            binding.pbResult.isVisible = false
            binding.containerErrorResult.isVisible = false
            binding.containerInternalResult.isVisible = false
            children.forEach { it.isVisible = false }
            container = Container.Pending
        }

        binding.btnTryAgain.setOnClickListener {
            if (isAuthError()) {
                Core.appRestarter.restartApp()
            } else {
                tryAgainListener?.invoke()
            }
        }
    }

    fun setTryAgainListener(onTryAgain: () -> Unit) {
        this.tryAgainListener = onTryAgain
    }

    private fun notifyUpdates() {
        val container = this.container
        binding.pbResult.isVisible = container is Container.Pending
        binding.containerErrorResult.isVisible = container is Container.Error
        binding.containerInternalResult.isVisible = container !is Container.Success

        if (container is Container.Error) {
            val exception = container.exception
            Core.logger.err(exception)
            binding.tvErrorResult.text = Core.errorHandler.getUserMessage(exception)
            binding.btnTryAgain.setText(
                if (isAuthError()) R.string.core_presentation_logout
                else R.string.core_presentation_try_again
            )
        }

        children.forEach {
            if (it != binding.root) {
                it.isVisible = container is Container.Success
            }
        }
    }

    private fun isAuthError() = container.let {
        it is Container.Error && it.exception is AuthException
    }
}