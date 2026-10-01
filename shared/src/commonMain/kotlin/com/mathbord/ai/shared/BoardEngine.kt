package com.mathbord.ai.shared

import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

/**
 * Platform-neutral whiteboard editing engine.
 *
 * It owns document mutations and edit history; rendering and input dispatch remain platform-specific.
 */
class BoardEngine(initialDocument: BoardDocument) {
    private var document: BoardDocument = initialDocument
    private val undoStack = ArrayDeque<BoardDocument>()
    private val redoStack = ArrayDeque<BoardDocument>()

    private var activeStroke: InkStroke? = null
    private var activeEraseSnapshot: BoardDocument? = null

    val snapshot: BoardDocument
        get() = document

    val canUndo: Boolean
        get() = undoStack.isNotEmpty()

    val canRedo: Boolean
        get() = redoStack.isNotEmpty()

    fun resize(width: Float, height: Float) {
        require(width >= 0f && height >= 0f)
        document = document.copy(width = width, height = height)
    }

    fun beginStroke(source: InputSource, tool: InkTool, point: StrokePoint): Boolean {
        if (tool != InkTool.PEN && tool != InkTool.HIGHLIGHTER) return false
        check(activeStroke == null) { "A stroke is already active" }
        activeStroke = InkStroke(
            id = newEntityId(),
            source = source,
            tool = tool,
            points = listOf(point)
        )
        return true
    }

    fun appendStrokePoint(point: StrokePoint): Boolean {
        val stroke = activeStroke ?: return false
        activeStroke = stroke.copy(points = stroke.points + point)
        return true
    }

    fun endStroke(): Boolean {
        val stroke = activeStroke ?: return false
        activeStroke = null
        return commitDocumentChange(document.copy(strokes = document.strokes + stroke))
    }

    fun beginErase() {
        check(activeEraseSnapshot == null) { "An erase gesture is already active" }
        activeEraseSnapshot = document
    }

    fun eraseAt(point: StrokePoint, radius: Float): Boolean {
        require(radius >= 0f)
        if (activeEraseSnapshot == null) beginErase()
        val filtered = document.strokes.filterNot { stroke ->
            strokeIntersectsRadius(stroke, point, radius)
        }
        if (filtered.size == document.strokes.size) return false
        document = document.copy(strokes = filtered)
        return true
    }

    fun endErase(): Boolean {
        val before = activeEraseSnapshot ?: return false
        activeEraseSnapshot = null
        if (before == document) return false
        undoStack.addLast(before)
        redoStack.clear()
        return true
    }

    fun clear(): Boolean {
        if (document.strokes.isEmpty()) return false
        return commitDocumentChange(document.copy(strokes = emptyList()))
    }

    fun undo(): Boolean {
        if (activeStroke != null) activeStroke = null
        if (activeEraseSnapshot != null) {
            activeEraseSnapshot = null
        }
        val previous = undoStack.removeLastOrNull() ?: return false
        redoStack.addLast(document)
        document = previous
        return true
    }

    fun redo(): Boolean {
        if (activeStroke != null) activeStroke = null
        if (activeEraseSnapshot != null) {
            activeEraseSnapshot = null
        }
        val next = redoStack.removeLastOrNull() ?: return false
        undoStack.addLast(document)
        document = next
        return true
    }

    private fun commitDocumentChange(next: BoardDocument): Boolean {
        if (next == document) return false
        undoStack.addLast(document)
        redoStack.clear()
        document = next
        return true
    }

    private fun strokeIntersectsRadius(
        stroke: InkStroke,
        point: StrokePoint,
        radius: Float
    ): Boolean {
        if (stroke.points.isEmpty()) return false
        if (stroke.points.size == 1) {
            return distance(stroke.points.first(), point) <= radius
        }

        for (index in 1 until stroke.points.size) {
            val a = stroke.points[index - 1]
            val b = stroke.points[index]
            if (distanceToSegment(point, a, b) <= radius) return true
        }
        return false
    }

    private fun distance(a: StrokePoint, b: StrokePoint): Float {
        val dx = a.x - b.x
        val dy = a.y - b.y
        return sqrt(dx * dx + dy * dy)
    }

    private fun distanceToSegment(
        p: StrokePoint,
        a: StrokePoint,
        b: StrokePoint
    ): Float {
        val dx = b.x - a.x
        val dy = b.y - a.y
        val lengthSquared = dx * dx + dy * dy

        if (lengthSquared <= 0f) return distance(p, a)

        val projection = ((p.x - a.x) * dx + (p.y - a.y) * dy) / lengthSquared
        val t = max(0f, min(1f, projection))
        val closestX = a.x + t * dx
        val closestY = a.y + t * dy
        val offsetX = p.x - closestX
        val offsetY = p.y - closestY
        return sqrt(offsetX * offsetX + offsetY * offsetY)
    }
}
