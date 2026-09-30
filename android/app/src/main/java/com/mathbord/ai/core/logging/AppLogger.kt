package com.mathbord.ai.core.logging

import android.util.Log

class AppLogger private constructor(private val enabled: Boolean) {
    fun debug(tag: String, message: String) { if (enabled) Log.d(tag, message) }
    fun info(tag: String, message: String) { if (enabled) Log.i(tag, message) }
    fun warning(tag: String, message: String, throwable: Throwable? = null) {
        if (enabled) Log.w(tag, message, throwable)
    }
    fun error(tag: String, message: String, throwable: Throwable? = null) {
        Log.e(tag, message, throwable)
    }
    companion object {
        fun createDefault() = AppLogger(enabled = true)
    }
}
