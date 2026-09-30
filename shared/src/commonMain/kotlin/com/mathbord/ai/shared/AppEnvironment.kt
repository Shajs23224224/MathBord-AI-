package com.mathbord.ai.shared

enum class AppEnvironment {
    DEVELOPMENT,
    STAGING,
    PRODUCTION
}

data class EnvironmentConfig(
    val environment: AppEnvironment,
    val apiBaseUrl: String?,
    val debugLoggingEnabled: Boolean
)
