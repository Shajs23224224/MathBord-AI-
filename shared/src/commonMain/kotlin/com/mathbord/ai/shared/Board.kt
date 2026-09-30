package com.mathbord.ai.shared

enum class InputSource {
    TOUCH,
    STYLUS,
    MOUSE,
    OTHER
}

data class BoardReference(
    val id: EntityId
)

interface BoardPort {
    suspend fun open(reference: BoardReference): BoardReference
    suspend fun create(): BoardReference
}
