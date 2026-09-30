package com.mathbord.ai.shared

@JvmInline
value class MathExpression(val source: String) {
    init {
        require(source.isNotBlank()) { "MathExpression cannot be blank" }
    }
}

sealed interface MathParseResult {
    data class Success(val expression: MathExpression) : MathParseResult
    data class Failure(val code: String) : MathParseResult
}

sealed interface MathValidationResult {
    data object Valid : MathValidationResult
    data class Invalid(val code: String) : MathValidationResult
    data object Unsupported : MathValidationResult
}

interface MathEngine {
    suspend fun parse(input: String): MathParseResult
    suspend fun validate(expression: MathExpression): MathValidationResult
}
