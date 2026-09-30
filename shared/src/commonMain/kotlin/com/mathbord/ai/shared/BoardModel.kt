package com.mathbord.ai.shared

enum class InkTool {
    PEN,
    HIGHLIGHTER,
    ERASER,
    SELECT
}

data class StrokePoint(
    val x: Float,
    val y: Float,
    val pressure: Float = 1f,
    val timestampEpochMillis: Long? = null
)

data class InkStroke(
    val id: EntityId,
    val source: InputSource,
    val tool: InkTool,
    val points: List<StrokePoint>
) {
    init {
        require(points.isNotEmpty()) { "InkStroke requires at least one point" }
    }
}

data class BoardDocument(
    val id: EntityId,
    val width: Float,
    val height: Float,
    val strokes: List<InkStroke> = emptyList()
)