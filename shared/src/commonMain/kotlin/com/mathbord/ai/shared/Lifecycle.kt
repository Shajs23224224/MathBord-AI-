package com.mathbord.ai.shared

enum class AppLifecycleState {
    CREATED,
    ACTIVE,
    INACTIVE,
    DESTROYED
}

interface AppLifecycleObserver {
    fun onStateChanged(state: AppLifecycleState)
}