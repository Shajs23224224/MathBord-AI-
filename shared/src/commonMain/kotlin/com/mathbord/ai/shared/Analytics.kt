package com.mathbord.ai.shared

data class AnalyticsEvent(
    val name: String,
    val parameters: Map<String, String> = emptyMap()
)

interface AnalyticsTracker {
    fun track(event: AnalyticsEvent)
}

object NoOpAnalyticsTracker : AnalyticsTracker {
    override fun track(event: AnalyticsEvent) = Unit
}
