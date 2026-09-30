package com.mathbord.ai.shared

sealed interface ConnectivityStatus {
    data object Connected : ConnectivityStatus
    data object Disconnected : ConnectivityStatus
    data object Unknown : ConnectivityStatus
}

interface ConnectivityObserver {
    fun currentStatus(): ConnectivityStatus
}
