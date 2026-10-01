package com.mathbord.ai.shared

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BoardEngineTest {
    private fun engine(): BoardEngine =
        BoardEngine(
            BoardDocument(
                id = EntityId("board-test"),
                width = 1000f,
                height = 1000f
            )
        )

    private fun point(x: Float, y: Float) = StrokePoint(x = x, y = y)

    @Test
    fun commitsStrokeAndSupportsUndoRedo() {
        val engine = engine()

        assertTrue(engine.beginStroke(InputSource.TOUCH, InkTool.PEN, point(10f, 10f)))
        engine.appendStrokePoint(point(20f, 20f))
        assertTrue(engine.endStroke())

        assertEquals(1, engine.snapshot.strokes.size)
        assertTrue(engine.canUndo)

        assertTrue(engine.undo())
        assertEquals(0, engine.snapshot.strokes.size)
        assertTrue(engine.canRedo)

        assertTrue(engine.redo())
        assertEquals(1, engine.snapshot.strokes.size)
    }

    @Test
    fun eraserRemovesIntersectingStrokeAndSupportsUndo() {
        val engine = engine()

        engine.beginStroke(InputSource.STYLUS, InkTool.PEN, point(10f, 10f))
        engine.appendStrokePoint(point(100f, 10f))
        engine.endStroke()

        engine.beginErase()
        assertTrue(engine.eraseAt(point(50f, 10f), radius = 5f))
        assertTrue(engine.endErase())
        assertEquals(0, engine.snapshot.strokes.size)

        assertTrue(engine.undo())
        assertEquals(1, engine.snapshot.strokes.size)
    }

    @Test
    fun resizeKeepsStrokes() {
        val engine = engine()

        engine.beginStroke(InputSource.TOUCH, InkTool.PEN, point(3f, 4f))
        engine.endStroke()
        engine.resize(1200f, 800f)

        assertEquals(1200f, engine.snapshot.width)
        assertEquals(800f, engine.snapshot.height)
        assertEquals(1, engine.snapshot.strokes.size)
    }
}
