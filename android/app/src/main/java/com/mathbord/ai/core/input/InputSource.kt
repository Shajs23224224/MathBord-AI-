package com.mathbord.ai.core.input

import androidx.compose.ui.input.pointer.PointerType

enum class InputSource {
    TOUCH,
    STYLUS,
    MOUSE,
    OTHER
}

fun PointerType.toInputSource(): InputSource = when (this) {
    PointerType.Touch -> InputSource.TOUCH
    PointerType.Stylus, PointerType.Eraser -> InputSource.STYLUS
    PointerType.Mouse -> InputSource.MOUSE
    else -> InputSource.OTHER
}
