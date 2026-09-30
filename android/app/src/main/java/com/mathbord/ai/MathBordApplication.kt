package com.mathbord.ai

import android.app.Application
import com.mathbord.ai.core.di.AppContainer

class MathBordApplication : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer.createDefault()
    }
}
