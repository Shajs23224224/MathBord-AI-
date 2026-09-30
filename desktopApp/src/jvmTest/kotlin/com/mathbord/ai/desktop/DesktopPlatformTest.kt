package com.mathbord.ai.desktop

import com.mathbord.ai.shared.PlatformKind
import com.mathbord.ai.shared.currentPlatform
import kotlin.test.Test
import kotlin.test.assertEquals

class DesktopPlatformTest {
    @Test
    fun desktopRuntimeReportsDesktopPlatform() {
        assertEquals(PlatformKind.DESKTOP, currentPlatform())
    }
}