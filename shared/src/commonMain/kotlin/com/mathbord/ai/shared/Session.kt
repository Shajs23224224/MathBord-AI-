package com.mathbord.ai.shared

data class MathSession(
    val id: EntityId,
    val createdAtEpochMillis: Long,
    val updatedAtEpochMillis: Long,
    val boardId: EntityId
)