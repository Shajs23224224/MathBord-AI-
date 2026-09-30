package com.mathbord.ai.shared

enum class LogLevel {
    DEBUG,
    INFO,
    WARNING,
    ERROR
}

data class LogEvent(
    val level: LogLevel,
    val tag: String,
    val message: String
)

interface AppLogSink {
    fun write(event: LogEvent)
}
