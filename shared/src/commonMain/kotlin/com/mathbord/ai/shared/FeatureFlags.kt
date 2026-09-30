package com.mathbord.ai.shared

interface FeatureFlagProvider {
    fun isEnabled(key: String): Boolean
}

object EmptyFeatureFlagProvider : FeatureFlagProvider {
    override fun isEnabled(key: String): Boolean = false
}
