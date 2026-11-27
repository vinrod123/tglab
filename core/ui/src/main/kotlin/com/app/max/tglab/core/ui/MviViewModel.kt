package com.app.max.tglab.core.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class MviViewModel<S : MviViewModel.UiState, I : MviViewModel.UiIntent, E : MviViewModel.UiEffect>(
    initialState: S,
) : ViewModel() {
    interface UiState

    interface UiIntent

    interface UiEffect

    private val mutableState = MutableStateFlow(initialState)
    val state: StateFlow<S> = mutableState.asStateFlow()

    private val effectChannel = Channel<E>(Channel.BUFFERED)
    val effect: Flow<E> = effectChannel.receiveAsFlow()

    fun onIntent(intent: I) {
        viewModelScope.launch {
            handleIntent(intent)
        }
    }

    protected abstract suspend fun handleIntent(intent: I)

    protected fun updateState(transform: (S) -> S) {
        mutableState.update(transform)
    }

    protected fun sendEffect(effect: E) {
        viewModelScope.launch {
            effectChannel.send(effect)
        }
    }
}
