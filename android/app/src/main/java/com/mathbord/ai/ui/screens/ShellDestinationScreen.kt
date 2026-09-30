package com.mathbord.ai.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mathbord.ai.ui.components.AsyncStateView
import com.mathbord.ai.ui.components.PlaceholderScreen
import com.mathbord.ai.ui.viewmodel.ShellEffect
import com.mathbord.ai.ui.viewmodel.ShellEvent
import com.mathbord.ai.ui.viewmodel.ShellViewModel

@Composable
fun ShellDestinationScreen(
    title: String,
    description: String,
    viewModel: ShellViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is ShellEffect.Announce -> snackbarHostState.showSnackbar(effect.message)
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        AsyncStateView(
            status = state.status,
            modifier = Modifier.padding(paddingValues)
        ) {
            PlaceholderScreen(
                title = title,
                description = description,
                interactionCount = state.interactionCount,
                onInteraction = { viewModel.onEvent(ShellEvent.Interaction) }
            )
        }
    }
}
