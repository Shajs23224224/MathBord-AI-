package com.mathbord.ai.core.ui

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration

enum class WindowWidthClass { COMPACT, MEDIUM, EXPANDED }

object MathBordBreakpoints {
    const val COMPACT_MAX_DP = 599
    const val MEDIUM_MAX_DP = 839
}

fun windowWidthClass(screenWidthDp: Int): WindowWidthClass = when {
    screenWidthDp <= MathBordBreakpoints.COMPACT_MAX_DP -> WindowWidthClass.COMPACT
    screenWidthDp <= MathBordBreakpoints.MEDIUM_MAX_DP -> WindowWidthClass.MEDIUM
    else -> WindowWidthClass.EXPANDED
}

@Composable
fun rememberWindowWidthClass(): WindowWidthClass {
    val configuration: Configuration = LocalConfiguration.current
    return remember(configuration.screenWidthDp) {
        windowWidthClass(configuration.screenWidthDp)
    }
}
