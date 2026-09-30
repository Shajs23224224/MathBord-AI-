package com.mathbord.ai.shared

import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertEquals

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
    fun boardSerializationHasExplicitFormatBoundary() {
        assertEquals(BoardSerializationFormat.JSON.name, "JSON")
    }
}