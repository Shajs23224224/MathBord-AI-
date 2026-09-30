package com.mathbord.ai.core.di

import com.mathbord.ai.core.logging.AppLogger

interface AppDependencies {
    val logger: AppLogger
}

class AppContainer private constructor(
    override val logger: AppLogger
) : AppDependencies {
    companion object {
        fun createDefault() = AppContainer(AppLogger.createDefault())
    }
}
