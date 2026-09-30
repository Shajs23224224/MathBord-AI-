package com.mathbord.ai.shared

enum class PlatformKind {
    ANDROID,
    DESKTOP
}

expect fun currentPlatform(): PlatformKind
