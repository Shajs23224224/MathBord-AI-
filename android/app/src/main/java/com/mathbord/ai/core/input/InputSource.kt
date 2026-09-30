package com.mathbord.ai.core.input

import androidx.compose.ui.input.pointer.PointerType
import com.mathbord.ai.shared.InputSource as SharedInputSource

typealias InputSource = SharedInputSource

fun PointerType.toInputSource(): InputSource = when (this) {
    PointerType.Touch -> InputSource.TOUCH
    PointerType.Stylus, PointerType.Eraser -> InputSource.STYLUS
    PointerType.Mouse -> InputSource.MOUSE
    else -> InputSource.OTHER
}
