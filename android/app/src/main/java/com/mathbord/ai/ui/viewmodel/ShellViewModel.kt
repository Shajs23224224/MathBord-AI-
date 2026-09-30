package com.mathbord.ai.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mathbord.ai.core.state.UiEffect
import com.mathbord.ai.core.state.UiEvent
import com.mathbord.ai.core.ui.UiStatus
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ShellUiState(
    val status: UiStatus = UiStatus.Content,
    val interactionCount: Int = 0
)

sealed interface ShellEvent : UiEvent {
    data object Interaction : ShellEvent
}

sealed interface ShellEffect : UiEffect {
    data class Announce(val message: String) : ShellEffect
}

class ShellViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val INTERACTION_COUNT_KEY = "shell.interactionCount"
    }

    private val _uiState = MutableStateFlow(
        ShellUiState(
            interactionCount = savedStateHandle[INTERACTION_COUNT_KEY] ?: 0
        )
    )
    val uiState = _uiState.asStateFlow()

    private val _effects = MutableSharedFlow<ShellEffect>(extraBufferCapacity = 1)
    val effects = _effects.asSharedFlow()

    fun onEvent(event: ShellEvent) {
        when (event) {
            ShellEvent.Interaction -> {
                val nextCount = _uiState.value.interactionCount + 1
                savedStateHandle[INTERACTION_COUNT_KEY] = nextCount
                _uiState.update { it.copy(interactionCount = nextCount) }

                viewModelScope.launch {
                    _effects.emit(ShellEffect.Announce("Estado local actualizado."))
                }
            }
        }
    }
}
