package com.mcmouse88.multimodulefeature.core.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mcmouse88.multimodulefeature.core.common.*
import com.mcmouse88.multimodulefeature.core.presentation.assignable.Assignable
import com.mcmouse88.multimodulefeature.core.presentation.assignable.LiveValueAssignable
import com.mcmouse88.multimodulefeature.core.presentation.assignable.StateFlowAssignable
import com.mcmouse88.multimodulefeature.core.presentation.live.Event
import com.mcmouse88.multimodulefeature.core.presentation.live.LiveEventValue
import com.mcmouse88.multimodulefeature.core.presentation.live.LiveValue
import com.mcmouse88.multimodulefeature.core.presentation.live.MutableLiveValue
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

@OptIn(FlowPreview::class)
open class BaseViewModel : ViewModel() {

    protected val viewModelScope: CoroutineScope by lazy {
        val errorHandler = CoroutineExceptionHandler { _, exception ->
            Core.errorHandler.handleError(exception)
        }
        CoroutineScope(SupervisorJob() + Dispatchers.Main + errorHandler)
    }

    protected val resources: Resources get() = Core.resources

    protected val commonUi: CommonUi get() = Core.commonUi

    protected val logger: Logger get() = Core.logger

    private val debounceFlow = MutableSharedFlow<() -> Unit>(
        replay = 1,
        extraBufferCapacity = 42
    )

    protected var <T> LiveValue<T>.value: T
        get() = this.requireValue()
        set(value) {
            (this as? MutableLiveValue)?.setValue(value)
        }

    init {
        viewModelScope.launch {
            debounceFlow.sample(Core.debouncePeriodMillis).collect {
                it()
            }
        }
    }

    protected fun <T> liveValue(): LiveValue<T> {
        return MutableLiveValue()
    }

    protected fun <T> liveValue(value: T): LiveValue<T> {
        return MutableLiveValue(MutableLiveData(value))
    }

    protected fun <T> liveEvent(): LiveEventValue<T> {
        return MutableLiveValue()
    }

    protected fun <T> LiveEventValue<T>.publish(event: T) {
        this.value = Event(event)
    }

    protected fun <T> loadScreenInto(
        stateFlow: MutableStateFlow<Container<T>>,
        errorHandler: ((Exception) -> Unit)? = null,
        loader: suspend () -> T
    ) {
        loadScreenInto(StateFlowAssignable(stateFlow), errorHandler, loader)
    }

    protected fun <T> loadScreenInto(
        liveValue: LiveValue<Container<T>>,
        errorHandler: ((Exception) -> Unit)? = null,
        loader: suspend () -> T
    ) {
        loadScreenInto(LiveValueAssignable(liveValue), errorHandler, loader)
    }

    private fun <T> loadScreenInto(
        assignable: Assignable<Container<T>>,
        errorHandler: ((Exception) -> Unit)? = null,
        loader: suspend () -> T
    ) {
        viewModelScope.launch {
            try {
                assignable.setValue(Container.Pending)
                val value = loader()
                assignable.setValue(Container.Success(value))
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                errorHandler?.invoke(e)
                assignable.setValue(Container.Error(e))
            }
        }
    }

    protected fun <T> Flow<T>.toLiveValue(initialValue: T? = null): LiveValue<T> {
        val liveValue = if (initialValue != null) liveValue<T>(initialValue)
        else liveValue()

        viewModelScope.launch {
            collect {
                liveValue.value = it
            }
        }
        return liveValue
    }

    protected fun debounce(block: () -> Unit) {
        debounceFlow.tryEmit(block)
    }

    protected fun showErrorDialog(message: String) {
        Core.globalScope.launch {
            commonUi.alertDialog(
                AlertDialogConfig(
                    title = resources.getString(R.string.core_presentation_general_error_title),
                    message = message,
                    positiveButton = resources.getString(R.string.core_presentation_general_error_ok)
                )
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}