package com.mathbord.ai.shared

data class PlatformCapabilities(
    val touch: Boolean,
    val stylus: Boolean,
    val mouse: Boolean,
    val keyboard: Boolean,
    val camera: Boolean,
    val fileSystem: Boolean
)

interface PlatformCapabilitiesProvider {
    fun capabilities(): PlatformCapabilities
}