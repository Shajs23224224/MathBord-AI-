package com.mathbord.ai.shared

enum class BoardSerializationFormat {
    JSON
    // Binary formats can be introduced later without changing BoardDocument.
}

interface BoardSerializer {
    fun serialize(document: BoardDocument, format: BoardSerializationFormat): ByteArray
    fun deserialize(bytes: ByteArray, format: BoardSerializationFormat): BoardDocument
}