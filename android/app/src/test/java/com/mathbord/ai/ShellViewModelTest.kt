package com.mathbord.ai

import androidx.lifecycle.SavedStateHandle
import com.mathbord.ai.ui.viewmodel.ShellEvent
import com.mathbord.ai.ui.viewmodel.ShellViewModel
import org.junit.Assert.assertEquals
import org.junit.Test

class ShellViewModelTest {
    @Test
    fun interactionStateIsRestoredFromSavedStateHandle() {
        val savedState = SavedStateHandle()
        val first = ShellViewModel(savedState)
        first.onEvent(ShellEvent.Interaction)
        first.onEvent(ShellEvent.Interaction)

        val restored = ShellViewModel(savedState)
        assertEquals(2, restored.uiState.value.interactionCount)
    }
}