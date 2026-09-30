package com.mathbord.ai.core.state

sealed interface AppError {
    data object Network : AppError
    data object Timeout : AppError
    data object Validation : AppError
    data object Storage : AppError
    data object Recognition : AppError
    data object Ai : AppError
    data object Unknown : AppError
}
