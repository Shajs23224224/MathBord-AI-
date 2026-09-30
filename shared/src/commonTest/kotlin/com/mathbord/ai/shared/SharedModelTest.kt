package com.mathbord.ai.shared

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class SharedModelTest {
    @Test
    fun entityIdRejectsBlankValues() {
        assertFailsWith<IllegalArgumentException> { EntityId("") }
    }

    @Test
    fun boardStrokeRequiresAPoint() {
        assertFailsWith<IllegalArgumentException> {
            InkStroke(
                id = EntityId("stroke-1"),
                source = InputSource.TOUCH,
                tool = InkTool.PEN,
                points = emptyList()
            )
        }
    }

    @Test
    fun desktopAndAndroidUseStableEntityIdFormat() {
        val id = EntityId("session-123")
        assertEquals("session-123", id.value)
    }
}