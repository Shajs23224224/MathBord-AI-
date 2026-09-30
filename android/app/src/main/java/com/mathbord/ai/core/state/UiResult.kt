package com.mathbord.ai.core.state

sealed interface UiResult<out T> {
    data class Success<T>(val data: T) : UiResult<T>
    data class Failure(val error: AppError) : UiResult<Nothing>
    data object Loading : UiResult<Nothing>
}
