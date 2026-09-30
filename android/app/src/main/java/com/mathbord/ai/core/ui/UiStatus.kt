package com.mathbord.ai.core.ui

sealed interface UiStatus {
    data object Content : UiStatus
    data object Loading : UiStatus
    data object Empty : UiStatus
    data class Error(val message: String) : UiStatus
}
