package com.mathbord.ai.shared

@JvmInline
value class EntityId(val value: String) {
    init {
        require(value.isNotBlank()) { "EntityId cannot be blank" }
    }
}

expect fun newEntityId(): EntityId
