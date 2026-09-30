package com.mathbord.ai.shared

sealed interface PermissionStatus {
    data object Granted : PermissionStatus
    data object Denied : PermissionStatus
    data object NotRequired : PermissionStatus
}

interface PermissionPort {
    suspend fun request(permission: String): PermissionStatus
}

interface ClipboardPort {
    suspend fun copy(text: String)
    suspend fun read(): String?
}

interface SharePort {
    suspend fun shareText(text: String): Boolean
}