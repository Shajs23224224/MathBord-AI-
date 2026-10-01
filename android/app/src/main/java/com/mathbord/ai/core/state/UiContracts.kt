package com.mathbord.ai.core.state

interface UiEvent
interface UiEffect

data class AsyncUiState<T>(
    val data: T? = null,
    val isLoading: Boolean = false,
    val error: AppError? = null
)
