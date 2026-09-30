package com.mathbord.ai.shared

data class StoredAsset(
    val bytes: ByteArray,
    val contentType: String
)

interface FileStorage {
    suspend fun exists(key: String): Boolean
    suspend fun read(key: String): StoredAsset?
    suspend fun write(key: String, asset: StoredAsset)
    suspend fun delete(key: String)
}